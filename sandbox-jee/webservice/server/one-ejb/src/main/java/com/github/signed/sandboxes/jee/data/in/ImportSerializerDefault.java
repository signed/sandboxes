package com.github.signed.sandboxes.jee.data.in;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.joda.time.LocalDate;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ImportSerializerDefault implements ImportSerializer {

    @Inject
    private ImporterBean importerBean;

    private final Map<LocalDate, Queue<DataImportParameter>> jobQueuesPerDay = Maps.newHashMap();
    private final Map<LocalDate, QueueWatcher> queueWatchers = Maps.newHashMap();

    @Override
    public synchronized void putInImportQueue(DataImportParameter parameter) {
        Queue<DataImportParameter> jobQueue = jobQueuesPerDay.get(parameter.day());
        if (null == jobQueue) {
            jobQueue = new LinkedList<>();
            jobQueuesPerDay.put(parameter.day(), jobQueue);
            //queueWatchers.put(parameter.day(), new QueueWatcher(parameter.day(), importSerializer, importerBean));
        }
        jobQueue.add(parameter);
        scheduleWorkers();
    }


    public synchronized Optional<DataImportParameter> nextInQueue(LocalDate day) {
        DataImportParameter poll = jobQueuesPerDay.get(day).poll();
        return Optional.fromNullable(poll);
    }

    public static class QueueWatcher {
        private final LocalDate day;
        private final ImportSerializerDefault importSerializerDefault;
        private final ImporterBean importerBean;

        private DataImportParameter currentlyProcessing;

        public QueueWatcher(LocalDate day, ImportSerializerDefault importSerializerDefault, ImporterBean importerBean) {
            this.day = day;
            this.importSerializerDefault = importSerializerDefault;
            this.importerBean = importerBean;
        }

        public void processNextInQueue() {
            if (currentlyProcessing()) {
                return;
            }
            Optional<DataImportParameter> dataImportParameterOptional = importSerializerDefault.nextInQueue(day);
            if (dataImportParameterOptional.isPresent()) {
                currentlyProcessing = dataImportParameterOptional.get();
                Future<Integer> future = importerBean.performImport(currentlyProcessing);
                try {
                    Integer result = future.get();
                    System.out.println("imported data had length " + result);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                currentlyProcessing = null;
            }
        }

        private boolean currentlyProcessing() {
            return currentlyProcessing != null;
        }
    }

    @Asynchronous
    public void scheduleWorkers() {

    }

    @Asynchronous
    public void schedule(QueueWatcher watcher) {
        watcher.processNextInQueue();
    }

}

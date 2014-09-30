package com.github.signed.integration.camel.gui.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.github.signed.integration.camel.TemplateTrigger;
import com.github.signed.integration.camel.gui.StartStop;

public class CamelEvaluationCenterSwing {
    private final JFrame frame = new JFrame();
    private final JPanel mainPanel = new JPanel();
    private final StartStopSwing startStop = new StartStopSwing();
    private final TemplateTriggerSwing templateTrigger = new TemplateTriggerSwing("trigger template");
    private final TemplateTriggerSwing sftpTemplateTrigger = new TemplateTriggerSwing("sftp template");
    private final TemplateTriggerSwing sftpDownloadTrigger = new TemplateTriggerSwing("sftp download template");

    public CamelEvaluationCenterSwing() {
        frame.add(mainPanel);
        startStop.addTo(mainPanel);
        templateTrigger.addTo(mainPanel);
        sftpTemplateTrigger.addTo(mainPanel);
        sftpDownloadTrigger.addTo(mainPanel);
    }

    public void constructApplicationFrame() {
        frame.setSize(640, 480);
        frame.setTitle("Camel Evaluation Center");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
            }
        });
    }

    public StartStop startStop() {
        return startStop;
    }

    public TemplateTrigger templateTrigger() {
        return templateTrigger;
    }

    public TemplateTrigger sftpTemplateTrigger() {
        return sftpTemplateTrigger;
    }

    public TemplateTrigger sftpDownloadTrigger() {
        return sftpDownloadTrigger;
    }
}

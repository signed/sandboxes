package com.github.signed.tryanderror.resources.todo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

@Path("/todos")
public class TodoResource {

    private static int nextId = 0;

    private static int nextId() {
        return nextId++;
    }

    private static Map<Integer, Todo> todos = Maps.newConcurrentMap();

    static {

        DateTime now = new DateTime();
        DateTime yesterday = now.minusDays(1);
        addTodo("now", now);
        addTodo("yesterday 01", yesterday);
        addTodo("yesterday 02", yesterday.plusMinutes(1));
        addTodo("last month 01", now.minusMonths(1));
        addTodo("last month 02", now.minusMonths(1));
        addTodo("last Year 01", now.minusYears(1));
        addTodo("last Year 02", now.minusYears(1));
    }

    private static void addTodo(String name, DateTime reception) {
        int key = nextId();
        todos.put(key, new Todo(nextId(), reception, name, false));
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Collection<Todo> listAllTodos() {
        return todos.values();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/year")
    public Collection<Todo> listSomeStaticTodos() {
        return ImmutableList.of(new Todo(0, "I am static", false), new Todo(1, "A ist the first", true), new Todo(2, "B is in the middle", true));
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Todo listTodoById(@PathParam("id") int id) {
        return todos.get(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Todo updateTodoById(@PathParam("id") int id, Todo updatedData) {
        todos.remove(id);
        todos.put(id, updatedData);
        return updatedData;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Todo createNewTodo(Todo todo) {
        todo.id = nextId();

        DateTime receptionDate = getRandomReceptionDate();
        todo.setReceptionTime(receptionDate);
        todos.put(todo.id, todo);
        return todo;
    }

    private DateTime getRandomReceptionDate() {
        Long millis = DateTime.now().getMillis();

        return new DateTime(new Random().nextInt(millis.intValue()));
    }

    @DELETE
    @Path("{id}")
    public Response deleteTodoById(@PathParam("id")int id){
        todos.remove(id);
        return Response.ok().build();
    } 
}
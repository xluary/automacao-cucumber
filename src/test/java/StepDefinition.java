import com.google.gson.Gson;
import cucumberTest.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

public class StepDefinition {
    private Gson gson = new Gson();
    private User user;
    private User newUser;
    private TaskSemLocalDate taskParaCadastro;
    private Task task;


    private RequestSpecification request = RestAssured.given()
            .baseUri("http://localhost:8080/api")
            .contentType(ContentType.JSON);
    private Response response;

    @Dado("que o usuario foi cadastrado")
    public void cadastroUsuario(DataTable data) throws SQLException {
        user = createUserFromDataTable(data);
        DatabaseUtil.insertUser(user);
    }

    @Dado("que a busca por {string} no banco de dados retornou {string}" )
    public void buscarTarefaNaDataBase(String titulo, String status) throws SQLException {
        List<Task> listaDeAtividades = DatabaseUtil.findTaskByTitle(titulo);
        String taskEncontrada;
        if (listaDeAtividades.isEmpty()){
            taskEncontrada = "false";
        }else{
            taskEncontrada = "true";
        }
        assertEquals(status, taskEncontrada);
    }


    @Quando("cadastrar nova tarefa")
    public void cadastrarTarefa(DataTable data)  {
        task = createTaskFromDataTable(user, data);
        taskParaCadastro = removerLocalDate(task);
        String jsonBody = gson.toJson(taskParaCadastro) ;
        response = request.body(jsonBody).when().post("/tasks");
        response.prettyPrint();
    }

    @Quando("cadastrar nova tarefa sem titulo")
    public void cadastrarTarefaSemTitulo(DataTable data){
        task = createTaskFromDataTable(user, data);
        task.setTitle(null);
        taskParaCadastro = removerLocalDate(task);
        String jsonBody = gson.toJson(taskParaCadastro) ;
        response = request.body(jsonBody).when().post("/tasks");
        response.prettyPrint();
    }

    @Quando("cadastrar nova tarefa sem usuario")
    public void cadastrarTarefaSemUsuario(DataTable data){
        task = createTaskFromDataTable(user, data);
        task.setUser(null);
        taskParaCadastro = removerLocalDate(task);
        String jsonBody = gson.toJson(taskParaCadastro) ;
        response = request.body(jsonBody).when().post("/tasks");
        response.prettyPrint();
    }

    @Quando("criar novo usuario")
    public void cadastrarNovoUsuario(DataTable data) throws SQLException {
        newUser = createUserFromDataTable(data);
        DatabaseUtil.insertUser(newUser);
    }

    @Entao("a resposta devera ser {int}")
    public void verificarResposta(int status){
        response.then().statusCode(status);
    }


    @E("a tarefa no banco de dados devera ser igual a {string}")
    public void buscarTarefaDataBase(String status) throws SQLException {
        List<Task> listaDeAtividades = DatabaseUtil.findTaskByTitle(task.getTitle());
        String taskEncontrada;
        if (listaDeAtividades.isEmpty()){
            taskEncontrada = "false";
        }else{
            taskEncontrada = "true";
        }
        assertEquals(status, taskEncontrada);
    }

    @E("a tarefa foi cadastrada")
    public void cadastrarNovaTarefa(DataTable data) throws SQLException {
        task = createTaskFromDataTable(user, data);
        taskParaCadastro = removerLocalDate(task);
        String jsonBody = gson.toJson(taskParaCadastro) ;
        response = request.body(jsonBody).when().post("/tasks");
        response.prettyPrint();
    }


    @E("atualizar o usuario da tarefa")
    public void atualizarUsuarioTarefa() throws SQLException {
        List<Task> listaDeAtividades = DatabaseUtil.findTaskByTitle(task.getTitle());
        Long taskId = listaDeAtividades.get(0).getId();
        task.setUser(newUser);
        taskParaCadastro = removerLocalDate(task);
        String jsonBody = gson.toJson(taskParaCadastro) ;
        response = request.body(jsonBody).when().put("/tasks/" + taskId);
    }

    private User createUserFromDataTable(DataTable data) {
        User user = new User();
        data.asMaps().forEach(it -> {
            String name = it.get("name");
            if (name == null) {
                name = RandomStringUtils.randomAlphabetic(10);
            }
            String username = it.get("username");
            if (username == null) {
                username = RandomStringUtils.randomAlphabetic(10);
            }
            String password = it.get("password");
            if (password == null) {
                password = RandomStringUtils.random(10, true, true);
            }

            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);
        });

        return user;
    };

    private Task createTaskFromDataTable(User user, DataTable data) {
        Task task = new Task();
        data.asMaps().forEach(it ->{

            String description= it.get("description");
            if (description == null) {
                description = RandomStringUtils.randomAlphabetic(10);
            }
            String title = it.get("title");
            if (title == null) {
                title = RandomStringUtils.randomAlphabetic(10);
            }

            TaskStatus status;
            String statusEnviado= it.get("status");
            if(statusEnviado == null){
               status = TaskStatus.OPEN;
            } else{
                status =  TaskStatus.CLOSE;
            }

            task.setUser(user);
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
        });

        return task;
    };

    private TaskSemLocalDate removerLocalDate(Task task){
        TaskSemLocalDate taskAlterada = new TaskSemLocalDate();
        taskAlterada.setTitle(task.getTitle());
        taskAlterada.setDescription(task.getDescription());
        taskAlterada.setStatus(task.getStatus());
        taskAlterada.setUser(task.getUser());
        return taskAlterada;
    }
}

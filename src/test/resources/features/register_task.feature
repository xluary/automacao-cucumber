#language: pt
Funcionalidade: Registar tarefa


  Cenário: usuario cadastrado registra tarefa corretamente
    Dado o usuario foi cadastrado
    | name     |
    | isabella |
    Quando cadastrar nova tarefa
    | title                 |
    | trabalho de automacao |
    Entao a resposta devera ser 201
    E a tarefa no banco de dados devera ser igual a "true"

  Cenário: usuario cadastrado registra tarefa sem titulo
    Dado o usuario foi cadastrado
      | name     |
      | isabella |
    Quando cadastrar nova tarefa sem titulo
      | description  |
      | titulo vazio |
    Entao a resposta devera ser 400
    E a tarefa no banco de dados devera ser igual a "false"

  Cenário: cadastrar tarefa com status CLOSE
    Dado o usuario foi cadastrado
      | name     |
      | isabella |
    Quando cadastrar nova tarefa
      | status |
      | close  |
    Entao a resposta devera ser 400
    E a tarefa no banco de dados devera ser igual a "false"

  Cenário: cadastrar tarefa sem usuario
    Dado que a busca por "tarefa sem usuario" no banco de dados retornou "false"
    Quando cadastrar nova tarefa sem usuario
      | title |
      | tarefa sem usuario  |
    Entao a resposta devera ser 201
    E a tarefa no banco de dados devera ser igual a "true"




#language: pt
Funcionalidade: Atualizar tarefa

  Cenário: Atualizar usuario de tarefa
    Dado que o usuario foi cadastrado
      | name       |
      | original |
    E a tarefa foi cadastrada
      | title                 |
      | atualizacao de tarefa |
    Quando criar novo usuario
      | name       |
      | atualizado |
    E atualizar o usuario da tarefa
    Entao a resposta devera ser 200

  Cenário: Atualizar descricao da tarefa
    Dado que o usuario foi cadastrado
      | name       |
      | original |
    E a tarefa foi cadastrada
      | title                 |
      | Atualizar descricao da tarefa |
    Quando atualizar a tarefa
      | description |
      | atualizado  |
    Entao a resposta devera ser 200

  Cenário: Encerrar tarefa
    Dado que o usuario foi cadastrado
      | name       |
      | original   |
    E a tarefa foi cadastrada
      | description     |
      | Encerrar tarefa |
    Quando atualizar o status da tarefa para CLOSE
    Entao a resposta devera ser 200
    E o closedAt deve ser nao nulo

  Cenário: Atualizar tarefa encerrada
    Dado que o usuario foi cadastrado
      | name       |
      | original   |
    E a tarefa foi cadastrada
      | description     |
      | Atualizar tarefa encerrada |
    Quando atualizar o status da tarefa para CLOSE
    E atualizar o usuario da tarefa
    Entao a resposta devera ser 422






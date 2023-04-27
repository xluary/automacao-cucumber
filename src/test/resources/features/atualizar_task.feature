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


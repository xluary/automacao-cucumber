#language: pt
Funcionalidade: Deletar tarefa

  Cenário: Tentar deletar tarefa criada
    Dado que o usuario foi cadastrado
      | name       |
      | original   |
    E a tarefa foi cadastrada
      | description     |
      | deletar tarefa  |
    Quando tentar deletar a tarefa
    Entao a resposta devera ser 405
    E a mensagem deveria ser "Method Not Allowed"
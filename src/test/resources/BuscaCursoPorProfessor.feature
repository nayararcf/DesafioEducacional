Feature: Busca e visualizacao de cursos por Professor

   Eu Como usuario do sistema
   Preciso ter acesso aos cursos
   Para obter as informacoes que eu preciso

  Scenario: Acessar cursos da professora "Ena Loiola"
    Given que eu acesso o site "https://www.estrategiaconcursos.com.br/"
    And clico no menu "POR PROFESSOR"
    When digito o nome da professora "Ena Loiola" no campo Filtrar
    Then devo visualizar os cursos da professora Ena Loiola
    And devo visualizar os cursos da professora


  Scenario: Valor Parcelado bater com valor total do curso
    Given que eu acesso o site "https://www.estrategiaconcursos.com.br/"
    And filtro os cursos da professora "Ena Loiola"
    And clico no link Detalhes dos cursos disponiveis
    And clico na primeira opção de curso "Assinatura Básica 1 Ano - Cartão até 12x 89,90
    When visualizar o valor total do parcelamento na página de detalhamento
    Then o valor total do parcelamento exibido na página deve ser 1078,80


  Scenario: Valor da parcela do curso ser igual valor parcela no detalhamento
    Given que eu acesso o site "https://www.estrategiaconcursos.com.br/"
    And filtro os cursos da professora "Ena Loiola"
    And clico no link Detalhes dos cursos disponiveis
    And clico na primeira opção de parcelamento "Assinatura Básica 1 Ano - Cartão até 12x 89,90
    And visualizo o valor da parcela
    When visualizar se o valor da parcela da página detalhamento
    Then o valor deve ser 89,90

  Scenario: Quantidade de curos disponíveis na listagem coparado com quantidade de curso no detalhamento
    Given que eu acesso o site "https://www.estrategiaconcursos.com.br/"
    And filtro os cursos da professora "Ena Loiola"
    And visualizo a quantidade de cursos disponíveis
    When clicar no link detalhes
    Then a quantidade de cursos disponíves deve ser 86
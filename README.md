## Desafio Estratégia Concurso

## Visão Geral

Projeto criado para o desafio da vaga de QA Engineer da empresa Estratégia Concurso.

### Pré-requisitos

* Java
* Maven
* Chrome Driver (https://sites.google.com/a/chromium.org/chromedriver/downloads)

### Instalação

1. Clone o repositório
   ```sh
   git clone https://github.com/nayararcf/DesafioEducacional.git
   ```
2. Entrar na pasta do projeto e rodar os testes no prompt com o Maven passando a referência do Chrome Driver com `-D`
   ```sh
   mvn test -Dwebdriver.chrome.driver=C:\caminho\para\chromedriver.exe
   ```
 
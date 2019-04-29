# BD-API
Uma api para estabelecer conexões utilizando MySQL e SQLite em Java de uma maneira simples.

## Como utilizar?
A utilização dessa API é muito simples, lhe mostrarei em simples passos!



<h3> • Passo 1 </h3>
Para começar, temos de ter em mente que a partir de agora, o plugin passará a ter suporte a dois tipos
de conexão: MySQL E SQLite. Vamos começar indo até a configuração principal do seu plugin
e definir as configurações do armazenamento.


```yaml
Storage:
  TYPE: SQLite # MYSQL or SQLite
  # Apenas preencha as informações abaixo caso opte pelo MySQL! #
  # Only fill the informations below if you gonna use MySQL #
  Host: localhost
  Port: 3306
  Username: root
  Password: vertrigo
  Database: imoedas
```

Essa é a configuração base, coloque-a na sua config.yml, de preferência nas primeiras linhas, para quem for utilizar, conseguir ver sem problemas.

Coloquei algumas anotações para orientar, sinta-se a vontade para removê-las.




<h3> • Passo 2 </h3>

Acredite, a pior parte já passou, hahaha! Agora, baixe o código do GitHub e coloque nele no seu código. Certifique-se que ele ficou certinho

<img src="https://i.imgur.com/csvNvrm.png">

O código está bem comentado, acredito que não terá dificuldades a partir de agora.

<h3> • Passo 3 </h3>

Vá até a sua main (a classe que estende o JavaPlugin), e lá, instancie a classe principal da API, a **ConnectionManager.java**

<img src="https://i.imgur.com/eqcbgCo.png">

Pronto, a sua conexão com banco de dados está quase pronta. Você pode ver mais a fundo 
como ela funciona acessando o arquivo **ConnectionManager.java**, é lá que as conexões são feitas.

<h3> • Extras </h3>

Para ajudar ainda mais, eu criei um metódo de exemplo utilizando a API. Você pode vê-lo na **ConnectionManager.java**
ele chama justamente **metodoExemplo()**. Você sempre irá fazer os metódos de conexão MySQL/SQLite no arquivo principal da API.

<img src="https://i.imgur.com/JeOAHaJ.png">

Porque afinal de contas, esses dois armazenamentos são parecidos, logo, qualquer metódo das funções do SQL funcionarão sem problemas.



<h3> • Considerações Finais </h3>

Se eu ajudei, siga-me no GitHub, irei postar novas API's para auxiliar e projetos aqui também.
Sinta-se livre para dar fork nesse projeto e aprimorá-lo, assim como utilizá-lo em seus codigos livremente, contanto que deixe bem claro meus créditos e propriedade sobre esse projeto.

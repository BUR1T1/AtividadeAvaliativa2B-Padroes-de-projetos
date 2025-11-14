<body>
    <h1>Defesa das Soluções — Avaliação Prática de Padrões de Projeto</h1>
    <hr />
    <h2>Questão 1 — Strategy</h2>
    <p>
      Para resolver o problema de usar vários algoritmos de risco que podem mudar durante a execução, utilizei o padrão Strategy.
      Cada cálculo (VaR, Expected Shortfall e Stress Testing) foi colocado em classes separadas implementando a mesma interface.
      Assim, o sistema pode trocar de algoritmo sem precisar conhecer detalhes internos de cada um.
    </p>
    <p>
      O contexto financeiro complexo foi encapsulado na classe <em>RiskContext</em>, construída com um Builder para facilitar a leitura
      e evitar objetos mal configurados.
    </p>
    <p>
      A solução ficou flexível, fácil de expandir e segue o princípio da responsabilidade única.
    </p>
    <hr />
    <h2>Questão 2 — Adapter</h2>
    <p>
      Para integrar a interface moderna com o sistema bancário legado, implementei o padrão Adapter.
      O adaptador converte chamadas modernas como <em>autorizar(cartao, valor, moeda)</em> para o formato antigo exigido pelo legado,
      que funciona com <em>HashMap</em>.
    </p>
    <p>
      Também fiz o caminho inverso: as respostas do legado são traduzidas de volta para o modelo moderno.
      Adicionei o tratamento de campos obrigatórios do legado que não existem na interface atual e implementei
      o mapeamento exigido das moedas (USD=1, EUR=2, BRL=3).
    </p>
    <p>
      Isso elimina a necessidade de alterar o sistema legado e mantém todo o processo de conversão isolado em um único ponto.
    </p>
    <hr />
    <h2>Questão 3 — State</h2>
    <p>
      Para controlar os estados da usina nuclear, utilizei o padrão State.
      Cada estado foi colocado em uma classe própria com suas regras de transição,
      evitando grandes blocos de condicionais e deixando o comportamento mais claro.
    </p>
    <p>
      As regras mais importantes foram implementadas: condições de temperatura, tempo,
      falhas e a regra especial de que o estado EMERGENCIA só pode ser atingido após o ALERTA_VERMELHO.
    </p>
    <p>
      Também adicionei o modo de manutenção, que temporariamente altera o comportamento normal da máquina de estados.
    </p>
    <hr />
    <h2>Questão 4 — Chain of Responsibility</h2>
    <p>
      Para validar os documentos fiscais aplicando várias regras diferentes, utilizei o padrão Chain of Responsibility.
      Cada validador (schema XML, certificado, regras fiscais, banco de dados e SEFAZ) tem sua função isolada,
      e a cadeia executa cada um na ordem correta.
    </p>
    <p>
      A cadeia inclui:</p>
      <ul>
        <li>validações condicionais (alguns validadores só rodam se os anteriores passarem)</li>
        <li>circuit breaker após três falhas</li>
        <li>rollback quando um validador modifica o documento (como no banco de dados)</li>
        <li>timeout individual para cada validador</li>
      </ul>
    <p>
      Isso deixou a solução robusta, extensível e compatível com os requisitos reais de um sistema fiscal.
    </p>
    <hr />

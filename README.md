# 🚀 Conversor_NFA_em_DFA
Objetivo: desenvolver um código que receba um arquivo JSON representando um Autômato Finito Não-Determinístico (NFA). O programa deverá ser capaz de converter o NFA em um Autômato Finito Determinístico (DFA)

> MAVEN

> Dependência utilizada: com.googlecode.json-simple

# 📜 Objetivos:
    [ X ] **Criar conversor .json em NFA; **
    [ X ] **Criar metodo para criação do conjunto das partes;**
    [ X ] **Criar conversor NFA em DFA.**
    [ X ] **Fazer tratamento de erros em cada etapa do NFA e DFA para verificar se são válidos**
    [ X ] **Criar conversor de DFA para .json**

# 💾 Status do prjeto
> :construction: Projeto finalizado :construction:

# 📦 Disposição do programa
     .
     ├── conversor_nfa_dfa
     │   └── src
     │       └── main
     │           └── java
     |                └── src
     |                    ├── AutomatoFinito.java
     |                    ├── DFA.java
     |                    ├── NFA.java
     |                    └── Main.java
     ├── target
     ├── README.md
     ├── pom.xml
     └── exemplo.json
        ├── blackjack_test.exs
        └── test_helper.exs
    
     .

# 🔧 Principais Funções

    Main.java
        → main(String[] args);
        Função de execução principal do programa;
        Recebe como entrada o diretório, nome do arquivo json lido e converte em objeto do tipo JSON.
    
    NFA.java
        → NFAfromJSON(JSONObject json);
        Converte um objeto do tipo JSON em um objeto da classe NFA.
    
    DFA.java
        → DFAfromNFA(NFA nfa);
        Converte um objeto da classe NFA em um objeto da classe DFA;
        Inlcui o passo a passo de conversão NFA em um DFA.
        
        → DFAtoJSON();
        Converte um DFA em um JSON;

# ✒️ Autores: 
| [<img src="https://avatars.githubusercontent.com/u/99749672?v=4" width=115><br><sub>Gabriel Alexandre</sub>](https://https://github.com/aieFaria) |  [<img src="https://lh3.googleusercontent.com/a-/ALV-UjUSbAUZs8fIDLpE2IxgftQvn59uYcg5JtGjnglwGdLloMGgyXM=s50-c-k-no" width=115><br><sub>Isaac Jerferson</sub>](https://github.com/guilhermeonrails) |  [<img src="https://avatars.githubusercontent.com/u/160502160?v=4" width=115><br><sub>Raislson Bernardo</sub>](https://github.com/alexfelipe) |
| :---: | :---: | :---: |



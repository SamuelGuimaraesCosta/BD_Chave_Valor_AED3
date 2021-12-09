# Banco de Dados Chave Valor AED3

Projeto feito em offline, adicionado posteriormente em repositório GitHub.

# Integrantes 
Samuel Guimaraes Costa

Rennan Moreira Eckhardt Tavares

#Especificaçao Do Trabalho 
1. Contexto
Um banco de dados de chave-valor usa um dicionário para armazenar dados, como ilustrado
na Figura 1. Nessa organização, dados são estruturados em registros contendo dois campos:
uma chave que identifica unicamente aquele registro no banco de dados; e um valor que ar-
mazena o dado propriamente dito. Chaves e valores podem ter tipos quaisquer e registros não
possuem nenhuma relação entre si. Portanto, programas possuem máxima flexibilidade para
gerenciar dados armazenados. Tipicamente, as seguintes operações são fornecidas:
• Inserção: insere um novo objeto no banco de dados.
• Remoção: remove um objeto do banco de dados.
• Busca: recupera um objeto no banco de dados.
• Atualização: atualiza um determinado objeto no banco de dados.
Bancos de dados de chave-valor são altamente particionáveis e permitem maior escalabilidade
horizontal, em contrapartida a banco de dados relacionais. Por esse motivo, banco de dados
de chave-valor têm ganhado cada vez mais espaço em aplicações atuais, principalmente no
contexto da cloud. Alguns exemplos e banco dedados de chave-valor amplamente utilizados
são o Redis, DynamoDB, LevelDB and MemcachedDB.

2. Descrição
Nesse trabalho você deverá desenvolver um banco de dados de chave-valor com os seguintes
requisitos funcionais:
• Inserir / Remover / Buscar / Atualizar objetos no banco de dados. Objetos podem ser de
qualquer tipo (primitivo ou composto). Porém, o identificador de um registro deve ser
obrigatoriamente do tipo inteiro sem sinal.
• Listar / Filtrar objetos no banco de dados. Objetos no banco de dados podem ser listados
e/ou filtrados segundo uma chave de ordenação e um critério informada pela aplicação
cliente. Essa chave deve ser necessariamente do tipo inteiro sem sinal.
• Compactar / Descompactar o banco de dados. Por um lado, para fins de backup, o banco
de dados deve ser capaz de compactar e armazenar em um arquivo todos os seus regis-
tros. Por outro lado, com o intuito de suportar a restauração de um backup, o banco de
dados deve ser capaz de carregar de um arquivo compactado todos os seus registros.
Além dos requisitos funcionais listados anteriormente, o seu banco de dados de chave-valor
deve apresentar os seguintes requisitos não funcionais:
• Persistir em um ou mais arquivos os registros do seu banco de dados.
• Utilizar arquivo(s) indexado(s) para manter a estrutura da sua base de dados.
• Realizar a ordenação de registros com algoritmos de ordenação em memória secundária.
• Suportar o algoritmo de seleção para ordenação em memória secundária.
• Suportar o algoritmo de intercalação balanceada para ordenação em memória secundária.
• Suportar a Compactação/Descompactação usando o algoritmo de Huffman.
• Suportar a Compactação/Descompactação usando o algoritmo LZW.

3. Especificações Técnicas
O programa deve suportar a seguinte interface de linha de comando:
simpledb [cmd]
Manipula registros do banco de dados de chave-valor SimpleDB usando a operação espe-
cificada por cmd. As seguintes operações são suportadas:
--insert=<sort-key,value>
Insere um objeto no banco de dados. A chave de ordenação (sort-key) é um inteiro
positivo. O objeto é codificado em uma cadeia de caracteres pela aplicação cliente. Ao
concluir a operação, a chave do objeto inserido é impresso na saída padrão. Objetos
são gravados no arquivo simpledb.db.
--remove=<key>
Remove do banco de dados o objeto que é identificado pela chave key. Objetos são
removidos do arquivo simpledb.db.
--search=<key>
Busca no banco de dados objeto o que é identificado pela chave key. Caso o objeto seja
encontrado, o objeto (codificado por uma cadeia de caracteres) e sua chave de orde-
nação são impressos na saída padrão. Objetos são buscados no arquivo simple.db.
--update=<key,sort-key,value>
Atualiza o objeto que é identificado pela chave key. A chave de ordenação (sort-key)
é um inteiro positivo. O objeto é codificado em uma cadeia de caracteres pela aplica-
ção cliente. Objetos são buscados e alterados no arquivo simple.db.
--list=<expr> | --reverse-list=<expr>
Lista em ordem crescente (--list) ou em ordem decrescente (--reverse-list)
todos os objetos que possam uma chave de ordenação que atenda aos critérios es-
pecificados pela expressão expr. Objetos são buscados no arquivo simple.db. A
expressão expr deve aceitar qualquer operação lógica simples envolvendo a chave:
• key>X: objetos que possuem chave de ordenação maior que X.
• key<X: objetos que possuem chave de ordenação menor que X.
• key=X: objetos que possuem chave ordenação igual a X.
• key>=X: objetos que possuem chave de ordenação maior ou igual que X.
• key<=X: objetos que possuem chave de ordenação menor ou igual que X.
--compress=[huffman|lzw]
Compacta os registros do banco de dados usando o algoritmo de Codificação de Huff-
man ou o Algoritmo de Compressão LZW. O banco de dados compactado é salvo em
um arquivo nomeado simpledb.[huffman|lzw]. Objetos a serem compactados
são lidos do arquivo simple.db.
--decompress=[huffman|lzw]
Descompacta os registros do banco de dados usando o algoritmo de Codificação de
Huffman ou o Algoritmo de Compressão LZW. O banco de dados descompactado é
lido de um arquivo nomeado simpledb.[huffman|lzw]. Objetos descompactados
são escritos no arquivo simple.db.

4. Entrega
O programa a ser desenvolvido em uma das seguintes linguagens: C, C++, Java, GoLang ou
Rust. Você deve entregar o código fonte e outros artefatos produzidos.
O projeto deverá ser necessariamente desenvolvido usando o sistema de versionamento Git.
Para hospedar a árvore de fontes, qualquer plataforma de acesso público, como o GitHub,
BitBucket ou então GitLab, pode ser usada.
Na árvore de fontes do projeto, informações suficientes para a compilação do programa devem
ser fornecidas. Obrigatoriamente, a compilação deve suportar o ambiente Linux Ubuntu 20.04
e não deve exigir a instalação de pacotes e/ou programas de terceiros (ie. IDEs). Portanto,
recomenda-se que seja usado um sistema de compilação independente de plataforma, como o
make ou cmake (veja a Seção de Distribuição de Pontos Extras).

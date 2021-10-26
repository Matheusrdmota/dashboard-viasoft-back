# API REST Java

API Desenvolvida com o intuito de fornecer informações para o dashboard de informações de status de serviço dos portais das secretarias de fazenda dos estados.

A API disponibiliza um endpoint de POST para que um job que atualiza a cada 5 minutos persista as informações mais atualizadas do portal da fazenda em um banco de dados PostgreSQL. 

A API possui três endpoints de GET que são os seguintes:

1.  GET url/api/nf:

   Faz o retrieve do status atual de cada estado.

2. GET url/api/nf/{uf}:

   Faz o retrieve do status atual do estado passado na url.

3. GET url/api/nf/dt?date:

   Faz o retrieve do status dos estados na data passada como parâmetro

4. GET url/api/nf/indisponibility:

   Faz o retrieve do estado que apresentou maior quantidade de indisponibilidades.

   
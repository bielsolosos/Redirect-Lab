echo "Iniciando processo de Deploy com podman"

echo "Efeturando git pull"

git pull 

echo "Executando processo de buildar com podman compose"

podman compose down

podman compose build

echo "Subindo o container podman!!!"

podman compose up -d



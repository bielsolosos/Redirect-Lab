#!/bin/bash

# Script de Setup Inicial para VPS
# Este script prepara a VPS para receber deploys automáticos do GitHub Actions

set -e

echo "============================================"
echo "🚀 Setup Inicial da VPS para GitHub Actions"
echo "============================================"
echo ""

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para print colorido
print_success() { echo -e "${GREEN}✅ $1${NC}"; }
print_error() { echo -e "${RED}❌ $1${NC}"; }
print_warning() { echo -e "${YELLOW}⚠️  $1${NC}"; }
print_info() { echo -e "ℹ️  $1"; }

# Verificar se está rodando como root ou com sudo
if [ "$EUID" -eq 0 ]; then 
  print_warning "Rodando como root. Recomenda-se usar um usuário específico."
fi

echo ""
print_info "Este script irá:"
echo "  1. Instalar dependências necessárias (Git, Podman)"
echo "  2. Configurar SSH para GitHub Actions"
echo "  3. Clonar o repositório"
echo "  4. Configurar docker-compose.yml"
echo ""

read -p "Deseja continuar? (s/n) " -n 1 -r
echo
if [[ ! $REPLY =~ ^[SsYy]$ ]]; then
    print_error "Setup cancelado pelo usuário"
    exit 1
fi

# ====================
# 1. Verificar Sistema Operacional
# ====================
echo ""
print_info "Detectando sistema operacional..."

if [ -f /etc/os-release ]; then
    . /etc/os-release
    OS=$ID
    VER=$VERSION_ID
    print_success "Sistema detectado: $OS $VER"
else
    print_error "Não foi possível detectar o sistema operacional"
    exit 1
fi

# ====================
# 2. Instalar Git
# ====================
echo ""
print_info "Verificando instalação do Git..."

if command -v git &> /dev/null; then
    print_success "Git já está instalado ($(git --version))"
else
    print_warning "Git não encontrado. Instalando..."
    
    case $OS in
        ubuntu|debian)
            sudo apt update
            sudo apt install -y git
            ;;
        fedora|rhel|centos)
            sudo dnf install -y git
            ;;
        *)
            print_error "Sistema operacional não suportado: $OS"
            exit 1
            ;;
    esac
    
    print_success "Git instalado com sucesso"
fi

# ====================
# 3. Instalar Podman
# ====================
echo ""
print_info "Verificando instalação do Podman..."

if command -v podman &> /dev/null; then
    print_success "Podman já está instalado ($(podman --version))"
else
    print_warning "Podman não encontrado. Instalando..."
    
    case $OS in
        ubuntu|debian)
            sudo apt update
            sudo apt install -y podman podman-compose
            ;;
        fedora|rhel|centos)
            sudo dnf install -y podman podman-compose
            ;;
        *)
            print_error "Sistema operacional não suportado: $OS"
            exit 1
            ;;
    esac
    
    print_success "Podman instalado com sucesso"
fi

# Verificar podman-compose
if command -v podman-compose &> /dev/null; then
    print_success "podman-compose já está instalado"
else
    print_warning "Instalando podman-compose..."
    sudo pip3 install podman-compose || sudo apt install -y python3-pip && sudo pip3 install podman-compose
    print_success "podman-compose instalado"
fi

# ====================
# 4. Configurar SSH
# ====================
echo ""
print_info "Configurando acesso SSH..."

SSH_DIR="$HOME/.ssh"
AUTHORIZED_KEYS="$SSH_DIR/authorized_keys"

if [ ! -d "$SSH_DIR" ]; then
    mkdir -p "$SSH_DIR"
    chmod 700 "$SSH_DIR"
    print_success "Diretório .ssh criado"
fi

if [ ! -f "$AUTHORIZED_KEYS" ]; then
    touch "$AUTHORIZED_KEYS"
    chmod 600 "$AUTHORIZED_KEYS"
    print_success "Arquivo authorized_keys criado"
fi

echo ""
print_warning "IMPORTANTE: Adicione sua chave SSH pública ao arquivo:"
echo "  $AUTHORIZED_KEYS"
echo ""
print_info "Para adicionar a chave, execute na sua máquina local:"
echo "  ssh-copy-id -i ~/.ssh/id_rsa.pub $USER@$(hostname -I | awk '{print $1}')"
echo ""

# ====================
# 5. Configurar Repositório
# ====================
echo ""
read -p "📂 Digite o caminho onde deseja clonar o projeto (ex: /home/$USER/projetos): " PROJECT_BASE_DIR

if [ -z "$PROJECT_BASE_DIR" ]; then
    PROJECT_BASE_DIR="/home/$USER/projetos"
    print_info "Usando diretório padrão: $PROJECT_BASE_DIR"
fi

# Criar diretório se não existir
mkdir -p "$PROJECT_BASE_DIR"
cd "$PROJECT_BASE_DIR"

echo ""
read -p "🔗 Digite a URL do repositório Git (ex: https://github.com/user/repo.git): " REPO_URL

if [ -z "$REPO_URL" ]; then
    print_error "URL do repositório é obrigatória"
    exit 1
fi

# Extrair nome do repositório
REPO_NAME=$(basename -s .git "$REPO_URL")
PROJECT_DIR="$PROJECT_BASE_DIR/$REPO_NAME"

if [ -d "$PROJECT_DIR" ]; then
    print_warning "Diretório $PROJECT_DIR já existe"
    read -p "Deseja fazer git pull? (s/n) " -n 1 -r
    echo
    if [[ $REPLY =~ ^[SsYy]$ ]]; then
        cd "$PROJECT_DIR"
        git pull
        print_success "Repositório atualizado"
    fi
else
    print_info "Clonando repositório..."
    git clone "$REPO_URL" "$PROJECT_DIR"
    print_success "Repositório clonado em: $PROJECT_DIR"
fi

# ====================
# 6. Verificar docker-compose.yml
# ====================
echo ""
print_info "Verificando docker-compose.yml..."

cd "$PROJECT_DIR"

if [ -f "docker-compose.yml" ]; then
    print_success "docker-compose.yml encontrado"
elif [ -f "podman-compose.yml" ]; then
    print_success "podman-compose.yml encontrado"
else
    print_warning "Nenhum arquivo compose encontrado"
    echo ""
    print_info "Crie um arquivo docker-compose.yml no diretório do projeto"
fi

# ====================
# 7. Testar Podman Compose
# ====================
echo ""
print_info "Testando configuração do Podman Compose..."

if podman compose version &> /dev/null; then
    print_success "Podman Compose está funcionando corretamente"
else
    print_error "Erro ao executar podman compose"
    print_info "Tente executar: podman-compose --version"
fi

# ====================
# 8. Resumo Final
# ====================
echo ""
echo "============================================"
print_success "Setup Inicial Concluído!"
echo "============================================"
echo ""
print_info "📋 Resumo da Configuração:"
echo "  • Sistema Operacional: $OS $VER"
echo "  • Git: $(git --version | awk '{print $3}')"
echo "  • Podman: $(podman --version | awk '{print $3}')"
echo "  • Diretório do Projeto: $PROJECT_DIR"
echo "  • Usuário SSH: $USER"
echo "  • IP da VPS: $(hostname -I | awk '{print $1}')"
echo ""
print_warning "📝 Próximos Passos:"
echo ""
echo "1. Adicione sua chave SSH pública ao arquivo authorized_keys:"
echo "   ssh-copy-id -i ~/.ssh/id_rsa.pub $USER@$(hostname -I | awk '{print $1}')"
echo ""
echo "2. Configure as secrets no GitHub:"
echo "   VPS_HOST: $(hostname -I | awk '{print $1}')"
echo "   VPS_USER: $USER"
echo "   VPS_PROJECT_PATH: $PROJECT_DIR"
echo "   VPS_SSH_KEY: [sua chave privada SSH]"
echo ""
echo "3. Teste a conexão SSH:"
echo "   ssh $USER@$(hostname -I | awk '{print $1}')"
echo ""
echo "4. Execute o primeiro deploy manualmente:"
echo "   cd $PROJECT_DIR"
echo "   podman compose build"
echo "   podman compose up -d"
echo ""
print_success "Tudo pronto! 🚀"

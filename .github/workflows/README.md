# 🤖 GitHub Actions Workflows

Este diretório contém os workflows automatizados do projeto.

## 📂 Workflows Disponíveis

### 🚀 **Deploy to VPS with Podman** (`deploy.yml`)

Workflow automático de deploy para VPS usando Podman Compose.

**Trigger:**
- Push na branch `main`
- Execução manual (workflow_dispatch)

**Features:**
- ✅ Verificação de conexão SSH
- 📦 Pull automático do código
- 🔨 Build de imagens Docker
- 🚀 Deploy com zero-downtime
- 🏥 Health check pós-deploy
- 🧹 Limpeza automática de imagens antigas
- 📊 Relatório detalhado de deploy

**Secrets Necessárias:**
| Secret | Descrição |
|--------|-----------|
| `VPS_HOST` | IP ou hostname da VPS |
| `VPS_USER` | Usuário SSH |
| `VPS_SSH_KEY` | Chave privada SSH |
| `VPS_PROJECT_PATH` | Caminho do projeto na VPS |
| `VPS_PORT` | Porta SSH (opcional, padrão: 22) |

**Documentação Completa:** [DEPLOY_SETUP.md](../DEPLOY_SETUP.md)

---

## 📊 Status do Workflow

Para verificar o status dos workflows:
1. Acesse a aba **Actions** do repositório
2. Selecione o workflow desejado
3. Visualize os logs e resultados

---

## 🔧 Personalização

Para adicionar ou modificar workflows:
1. Edite os arquivos `.yml` neste diretório
2. Commit e push das alterações
3. O GitHub Actions detectará automaticamente as mudanças

---

## 🆘 Troubleshooting

### Workflow falhou?
1. Verifique os logs na aba Actions
2. Confirme se todas as secrets estão configuradas
3. Teste a conexão SSH manualmente
4. Verifique se o Podman está instalado na VPS

### Como executar manualmente?
1. Vá para **Actions**
2. Selecione **Deploy to VPS with Podman**
3. Clique em **Run workflow**
4. Escolha a branch e confirme

---

## 📚 Recursos

- [Documentação GitHub Actions](https://docs.github.com/actions)
- [Podman Documentation](https://docs.podman.io/)
- [Setup Guide Completo](../DEPLOY_SETUP.md)

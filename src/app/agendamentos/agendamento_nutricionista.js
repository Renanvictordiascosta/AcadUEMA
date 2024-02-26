import React, { useState ,useContext} from "react";
import { useNavigate, useParams } from 'react-router-dom'
import { AuthContext } from '../main/provetorAutenticacao'
import Card from "../components/card";
import FormGroup from "../components/form-group";
import SelectMenu from '../components/selectMenu'

import LocalStorageService from "../service/localstorageService";
import * as messages from '../components/toastr'
import { render } from "@testing-library/react";

import AgendamentoService from "../service/agendamentoService"
import AgendamentoTable from "./agendamento_table";
import { Dialog } from 'primereact/dialog'
import { Button } from 'primereact/button'

const TelaNutricionista = ({}) =>
{
    const navigate = useNavigate();
   
    const [showConfirmDialog, setShowConfirmDialog] = useState(false);
    const [agendamentoDeletar, setAgendamentoDeletar] = useState({});
    const [agendamentos, setAgendamentos] = useState([]);
    const usuarioLogado = LocalStorageService.obterItem('_usuario_logado'); 
    const [funcionario, setFuncionario] = useState(usuarioLogado.cpf);
    const agendamentoFiltro = {
       funcionario
    };
    const service = new AgendamentoService();
    const context = useContext(AuthContext);
    const goToSair = () => {
      context.encerrarSessao();
      navigate("/");
  }
    const buscar = () => {
        // // const usuarioLogado = LocalStorageService.obterItem('_usuario_logado');
        // setFuncionario(usuarioLogado.cpf);
        // console.log(funcionario);
        service.consultar(agendamentoFiltro)
            .then(resposta => {
              const lista = resposta.data;
              if (lista.length < 1) {
                messages.mensagemAlert('Nenhum resultado encontrado.');
              }
              setAgendamentos(lista);
            })
            .catch(error => {
              console.log(error);
            });
        }
        const abrirConfirmacao = agendamento => {
            setShowConfirmDialog(true);
            setAgendamentoDeletar(agendamento);
          };
        
          const cancelarDelecao = () => {
            setShowConfirmDialog(false);
            setAgendamentoDeletar({});
          };
        
          const deletar = () => {
            service
              .deletar(agendamentoDeletar.id)
              .then(response => {
                const updatedagendamento = agendamentos.filter(
                    agendamento => agendamento.id !== agendamentoDeletar.id
                );
                setAgendamentos(updatedagendamento);
                setShowConfirmDialog(false);
                messages.mensagemSucesso('Agendamento deletado com sucesso!');
              })
              .catch(error => {
                messages.mensagemErro('Ocorreu um erro ao tentar deletar o Lançamento');
              });
          };
        
    
        const alterarStatus = (agendamento, status) => {
            service
              .alterarStatus(agendamento.id, status)
              .then(response => {
                const index = agendamentos.findIndex(l => l.id === agendamento.id);
                if (index !== -1) {
                  const updatedagendamentos = [...agendamentos];
                  updatedagendamentos[index].status = status;
                  setAgendamentos(updatedagendamentos);
                }
                messages.mensagemSucesso('Status atualizado com sucesso!');
              })
              .catch(error => {
                messages.mensagemErro('Ocorreu um erro ao tentar atualizar o status');
              });
          };
        
          const confirmDialogFooter = (
            <div>
              <Button
                label="Confirmar"
                icon="pi pi-check"
                onClick={deletar}
              />
              <Button
                label="Cancelar"
                icon="pi pi-times"
                onClick={cancelarDelecao}
                className="p-button-secondary"
              />
            </div>
          );

          return(

            <Card title={`Agendamentos Marcados para ${usuarioLogado.nome}`}>
                  <div className="row">
                    <div className="col-md-6" style={{ margin: '2%' }}>
                      <div className="bs-component">
                      
                        <button
                          onClick={buscar}
                          type="button"
                          className="btn btn-success" style={{ marginRight: '2%' }}
                        >
                          <i className="pi pi-search"></i> Buscar
                        </button>
                        <button onClick={e => goToSair()} 
                                    className="btn btn-danger" style={{ marginRight: '2%' }}>
                                    <i className="pi pi-times"></i>Sair
                            </button>
                      </div>
                    </div>
                  </div>
                  <br />
                  <div className="row">
                    <div className="col-md-12">
                      <div className="bs-component">
                        <AgendamentoTable
                          agendamentos={agendamentos}
                          deleteAction={abrirConfirmacao}
                          alterarStatus={alterarStatus}
                        />
                      </div>
                    </div>
                  </div>
                  <div>
                    <Dialog
                      header="Confirmação"
                      visible={showConfirmDialog}
                      style={{ width: '50vw' }}
                      footer={confirmDialogFooter}
                      modal={true}
                      onHide={() => setShowConfirmDialog(false)}
                    >
                      Confirma a exclusão deste Agendamento?
                    </Dialog>
                  </div>
                </Card>
            
          )
    
}
export default TelaNutricionista
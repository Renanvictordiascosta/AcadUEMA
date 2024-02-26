import React, { useState } from "react";
import { useNavigate } from 'react-router-dom'

import Card from "../components/card";
import FormGroup from "../components/form-group";
import SelectMenu from '../components/selectMenu'
import LocalStorageService from "../service/localstorageService";
import * as messages from '../components/toastr'

import { Dialog } from 'primereact/dialog'
import { Button } from 'primereact/button'

import AgendamentoTable from "./agendamento_table";
import AgendamentoService from '../service/agendamentoService'

const ConsultarAgendamentos = ({ }) => {
  const navigate = useNavigate();
  const [horario, setHorario] = React.useState('');
  const [dia, setDia] = React.useState('');
  const [mes, setMes] = React.useState('');
  const [ano, setAno] = React.useState('');
  const [aluno, setAluno] = React.useState('');
  const [funcionario, setFuncionario] = React.useState('');

  const [showConfirmDialog, setShowConfirmDialog] = useState(false);
  const [agendamentoDeletar, setAgendamentoDeletar] = useState({});
  const [agendamentos, setAgendamentos] = useState([]);

  const service = new AgendamentoService();

  const agendamentoFiltro = {
    horario, dia, mes, ano, aluno, funcionario
  };

  const buscar = () => {
    service
      .consultar(agendamentoFiltro)
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

  const statuss = service.obterTipoAgendamento();
  const meses = service.obterListaMeses();

  return (

    <Card title="Agendamentos Marcados">
      <div className="row">
        <div className="col-md-6">
          <div className="bs-component">
            <FormGroup htmlFor="inputHorario" label="Horario: ">
              <input
                type="text"
                className="form-control"
                id="inputHorario"
                value={horario}
                onChange={e => setHorario(e.target.value)}
                placeholder="Digite o Horario"
              />
            </FormGroup>
            <FormGroup htmlFor="inputDia" label="Dia: ">
              <input
                type="text"
                className="form-control"
                id="inputDia"
                value={dia}
                onChange={e => setDia(e.target.value)}
                placeholder="Digite o Dia"
              />
            </FormGroup>

            <FormGroup htmlFor="inputMeses" label="Mês:">
              <SelectMenu
                id="inputMes"
                value={mes}
                onChange={e => setMes(e.target.value)}
                className="form-control"
                lista={meses}
              />
            </FormGroup>
            <FormGroup htmlFor="inputAno" label="Ano: ">
              <input
                type="text"
                className="form-control"
                id="inputAno"
                value={ano}
                onChange={e => setAno(e.target.value)}
                placeholder="Digite o Ano"
              />
            </FormGroup>
            <FormGroup htmlFor="inputCPF" label="CPF: ">
              <input
                type="text"
                className="form-control"
                id="inputCPF"
                value={aluno}
                onChange={e => setAluno(e.target.value)}
                placeholder="Digite o CPF do Aluno!"
              />
            </FormGroup>
            <FormGroup htmlFor="inputCPF" label="CPF: ">
              <input
                type="text"
                className="form-control"
                id="inputCPF"
                value={funcionario}
                onChange={e => setFuncionario(e.target.value)}
                placeholder="Digite o CPF do Funcionario!"
              />
            </FormGroup>
            <div className="row">
              <div className="col-md-6" style={{ margin: '2%' }}>
                <button
                  onClick={buscar}
                  type="button"
                  className="btn btn-success" style={{ marginRight: '2%' }}
                >
                  <i className="pi pi-search"></i> Buscar
                </button>
                <button onClick={e => navigate('/recepcao')}
                  className="btn btn-danger" style={{ marginLeft: '2%' }}>
                  <i className="pi pi-times"></i>Cancelar
                </button>
              </div>
            </div>
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
            //   editAction={editar}
            //   agendamentoAction={preparaFormularioAgendamentos }
            //   alterarStatus={alterarStatus}
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
export default ConsultarAgendamentos;
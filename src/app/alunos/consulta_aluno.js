import React, { useState } from "react";
import { useNavigate } from 'react-router-dom'

import Card from "../components/card";
import FormGroup from "../components/form-group";
import SelectMenu from '../components/selectMenu'
import AlunoTable from "./aluno_table";
import AlunoService from '../service/alunoService'
import LocalStorageService from "../service/localstorageService";
import * as messages from '../components/toastr'

import { Dialog } from 'primereact/dialog'
import { Button } from 'primereact/button'


const ConsultaAluno = ({ }) => {

  const navigate = useNavigate();
  const [nome, setNome] = React.useState('');
  const [genero, setGenero] = React.useState('');
  const [tipo, setTipo] = React.useState('');
  // const[status_do_aluno,setStatus] = React.useState('');
  const [showConfirmDialog, setShowConfirmDialog] = useState(false);
  const [alunoDeletar, setAlunoDeletar] = useState({});
  const [alunos, setAlunos] = useState([]);
  const service = new AlunoService();
  const alunoFiltro = {
    nome, genero, tipo
  };

  const buscar = () => {
    service
      .consultar(alunoFiltro)
      .then(resposta => {
        const lista = resposta.data;
        if (lista.length < 1) {
          messages.mensagemAlert('Nenhum resultado encontrado.');
        }
        setAlunos(lista);
      })
      .catch(error => {
        console.log(error);
      });
  }


  const editar = id => {
    navigate(`/cadastro-aluno/${id}`);
  };

  const abrirConfirmacao = aluno => {
    setShowConfirmDialog(true);
    setAlunoDeletar(aluno);
  };

  const cancelarDelecao = () => {
    setShowConfirmDialog(false);
    setAlunoDeletar({});
  };

  const deletar = () => {
    service
      .deletar(alunoDeletar.cpf)
      .then(response => {
        const updatedalunos = alunos.filter(
          aluno => aluno.cpf !== alunoDeletar.cpf
        );
        setAlunos(updatedalunos);
        setShowConfirmDialog(false);
        messages.mensagemSucesso('Lançamento deletado com sucesso!');
      })
      .catch(error => {
        messages.mensagemErro('Ocorreu um erro ao tentar deletar o Lançamento');
      });
  };

  const preparaFormularioAgendamentos = id => {
    navigate(`/agendamento-aluno/${id}`);
  };


  const alterarStatus = (aluno, status) => {
    service
      .alterarStatus(aluno.cpf, status)
      .then(response => {
        const index = alunos.findIndex(l => l.id === aluno.id);
        if (index !== -1) {
          const updatedalunos = [...alunos];
          updatedalunos[index].status = status;
          setAlunos(updatedalunos);
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

  const generos = service.obterTipoGenero();
  const tipos = service.obterTipoAluno();
  const statuss = service.obterTipoStatus();
  return (
    <Card title="Consultas de Aluno">
      <div className="row">
        <div className="col-md-6">
          <div className="bs-component">
            <FormGroup htmlFor="inputNome" label="Nome: ">
              <input
                type="text"
                className="form-control"
                id="inputNome"
                value={nome}
                onChange={e => setNome(e.target.value)}
                placeholder="Digite o Nome"
              />
            </FormGroup>

            <FormGroup htmlFor="inputGenero" label="Genero:">
              <SelectMenu
                id="inputGenero"
                value={genero}
                onChange={e => setGenero(e.target.value)}
                className="form-control"
                lista={generos}
              />
            </FormGroup>

            <FormGroup htmlFor="inputTipo" label="Tipo de Aluno: ">
              <SelectMenu
                id="inputTipo"
                value={tipo}
                onChange={e => setTipo(e.target.value)}
                className="form-control"
                lista={tipos}
              />
            </FormGroup>
            {/* <FormGroup htmlFor="inputStatus" label="Status:">
              <SelectMenu
                id="inputStatus"
                value={status_do_aluno}
                onChange={e => setStatus(e.target.value)}
                className="form-control"
                lista={statuss}
              />
            </FormGroup> */}
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
            <AlunoTable
              alunos={alunos}
              deleteAction={abrirConfirmacao}
              editAction={editar}
              agendamentoAction={preparaFormularioAgendamentos}
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
          Confirma a exclusão deste Lançamento?
        </Dialog>
      </div>
    </Card>
  );

}
export default ConsultaAluno;
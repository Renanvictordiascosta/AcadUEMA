import React, { useState } from "react";
import { useNavigate } from 'react-router-dom'

import Card from "../components/card";
import FormGroup from "../components/form-group";
import SelectMenu from '../components/selectMenu'

import LocalStorageService from "../service/localstorageService";
import * as messages from '../components/toastr'

import FuncionarioService from '../service/funcionarioService'
import FuncionarioTable from '../rh/funcionario_table'
import { Dialog } from 'primereact/dialog'
import { Button } from 'primereact/button'


const ConsultaFuncionario = ({ }) => {

  const navigate = useNavigate();
  const [nome, setNome] = React.useState('');
  const [tipo, setTipo] = React.useState('');
  const [showConfirmDialog, setShowConfirmDialog] = useState(false);
  const [funcionarioDeletar, setFuncionarioDeletar] = useState({});
  const [funcionarios, setFuncionarios] = useState([]);
  const service = new FuncionarioService();
  const funcionarioFiltro = {
    nome, tipo
  };

  const buscar = () => {
    service
      .consultar(funcionarioFiltro)
      .then(resposta => {
        const lista = resposta.data;
        if (lista.length < 1) {
          messages.mensagemAlert('Nenhum resultado encontrado.');
        }
        setFuncionarios(lista);
      })
      .catch(error => {
        console.log(error);
      });
  }


  const editar = id => {
    navigate(`/cadastro-funcionario/${id}`);
  };

  const abrirConfirmacao = funcionario => {
    setShowConfirmDialog(true);
    setFuncionarioDeletar(funcionario);
  };

  const cancelarDelecao = () => {
    setShowConfirmDialog(false);
    setFuncionarioDeletar({});
  };

  const deletar = () => {
    service
      .deletar(funcionarioDeletar.cpf)
      .then(response => {
        const updatedfuncionarios = funcionarios.filter(
            funcionario=> funcionario.cpf !== funcionarioDeletar.cpf
        );
        setFuncionarios(updatedfuncionarios);
        setShowConfirmDialog(false);
        messages.mensagemSucesso('Funcionario deletado com sucesso!');
      })
      .catch(error => {
        messages.mensagemErro('Ocorreu um erro ao tentar deletar o Funcionario');
      });
  };


  const alterarStatus = (funcionario, status) => {
    service
      .alterarStatus(funcionario.cpf, status)
      .then(response => {
        const index = funcionarios.findIndex(l => l.id === funcionario.id);
        if (index !== -1) {
          const updatedfuncionarios = [...funcionarios];
          updatedfuncionarios[index].status = status;
          setFuncionarios(updatedfuncionarios);
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
  const tipos = service.obterTipoFuncionario();
  return (
    <Card title="Consultas de Funcionarios">
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

            <FormGroup htmlFor="inputTipo" label="Tipo de Funcionario: ">
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
                <button onClick={e => navigate('/admin')}
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
            <FuncionarioTable
              funcionarios={funcionarios}
              deleteAction={abrirConfirmacao}
              editAction={editar} 
            // alterarStatus={alterarStatus}
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
export default ConsultaFuncionario;
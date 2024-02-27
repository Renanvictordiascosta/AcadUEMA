import React, { useState } from "react";
import { useNavigate, useParams } from 'react-router-dom'

import Card from "../components/card";
import FormGroup from "../components/form-group";
import SelectMenu from '../components/selectMenu'

import LocalStorageService from "../service/localstorageService";
import * as messages from '../components/toastr'
import { render } from "@testing-library/react";

import AlunoService from "../service/alunoService";
import FuncionarioService from "../service/funcionarioService"
import AgendamentoService from "../service/agendamentoService"
import FuncionarioAgendamentoTable from '../rh/funcionario_table_agendamentos'
import { Dialog } from 'primereact/dialog'
import { Button } from 'primereact/button'

const CadastroAgendamento = ({ }) => {
    const navigate = useNavigate();
    const [horario, setHorario] = React.useState('');
    const [dia, setDia] = React.useState('');
    const [mes, setMes] = React.useState('');
    const [ano, setAno] = React.useState('');
    const [aluno, setAluno] = React.useState('');
    const [funcionario, setFuncionario] = React.useState('');
    const [funcionarioid, setFuncionarioid] = React.useState('');
    const [funcionarios, setFuncionarios] = useState([]);
    const agendamentoDados = {
        horario, dia, mes, ano, aluno, funcionario
    };
    const [showConfirmDialog, setShowConfirmDialog] = useState(false);
    const aluno_service = new AlunoService();
    const funcionario_service = new FuncionarioService();
    const service = new AgendamentoService();
    const funcionariofilltro = ''

    const params = useParams();
    if (params.id) {
        aluno_service
            .obterPorId(params.id)
            .then(response => {

                setAluno(response.data.cpf);
                // console.log(response.data.cpf);
            })
            .catch(erros => {
                messages.mensagemErro(erros.response.data);
            })

    }
    const buscar = () => {
        funcionario_service.consultar(funcionariofilltro)
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

    const escolherFuncionario = () => {
        funcionario_service.obterPorId(funcionarioid).then(response => {

            setFuncionario(response.data.cpf);

        })
            .catch(erros => {
                messages.mensagemErro(erros.response.data);
            })
        setShowConfirmDialog(false);

    }
    const abrirConfirmacao = func => {
        setShowConfirmDialog(true);
        setFuncionarioid(func);

    };

    const cancelarAgendamento = () => {
        setShowConfirmDialog(false);
        setFuncionarioid('');
    };

    const submit = () => {
        try {
            service.validar(agendamentoDados)
        }
        catch (erro) {
            const mensagens = erro.mensagens;
            mensagens.forEach(msg => messages.mensagemErro(msg));
            return false;

        }
        service.salvar(agendamentoDados)
            .then(resposta => {
                // navigate("");
                messages.mensagemSucesso('Agendamento Cadastrado Com Sucesso.');
            })
            .catch(error => {
                messages.mensagemErro(error.response.data)
            });

    }
    const confirmDialogFooter = (
        <div>
            <Button
                label="Confirmar"
                icon="pi pi-check"
                onClick={escolherFuncionario}
            />
            <Button
                label="Cancelar"
                icon="pi pi-times"
                onClick={cancelarAgendamento}
                className="p-button-secondary"
            />
        </div>
    );

    const meses = service.obterListaMeses();

    return (
        <Card title={'Agendamento Novos'}>
            <div className="row">
                <div className="col-md-12">
                    <FormGroup id="inputHorario" label="Horario:*" >
                        <input id="inputHorario" type="text"
                            className="form-control"
                            name="horario"
                            value={horario}
                            onChange={e => setHorario(e.target.value)} />
                    </FormGroup>
                </div>
            </div>
            <div className="row">
                <div className="col-md-12">
                    <FormGroup id="inputDia" label="Dia: *" >
                        <input id="inputDia" type="text"
                            className="form-control"
                            name="dia"
                            value={dia}
                            onChange={e => setDia(e.target.value)} />
                    </FormGroup>
                </div>
            </div>

            <div className="row">

                <div className="col-md-4">
                    <FormGroup id="inputMes" label="Mes: *">
                        <SelectMenu id="inputMes"
                            lista={meses}
                            name="meses"
                            value={meses}
                            onChange={e => setMes(e.target.value)}
                            className="form-control" />
                    </FormGroup>
                </div>

                <div className="col-md-4">
                    <FormGroup id="inputAno" label="Ano: *">
                        <input id="inputANo"
                            type="text"
                            name="ano"
                            value={ano}
                            onChange={e => setAno(e.target.value)}
                            className="form-control" />
                    </FormGroup>
                </div>
            </div>
            <div className="row">
                <div className="col-md-6" style={{ margin: '2%' }} >
                    <button
                        onClick={buscar}
                        type="button"
                        className="btn btn-success"  style={{ marginRight: '2%' }}
                    >
                        <i className="pi pi-search"></i> Buscar Funcionarios
                    </button>
                    <button onClick={submit}
                        className="btn btn-success"  style={{ margin: '2%' }}>
                        <i className="pi pi-save"></i> Salvar
                    </button>
                    <button onClick={e => navigate('/recepcao')}
                        className="btn btn-danger"  style={{ marginLeft: '2%' }}>
                        <i className="pi pi-times"></i>Cancelar
                    </button>
                </div>
            </div>
            <div className="row">
                <div className="col-md-12">
                    <div className="bs-component">
                        <FuncionarioAgendamentoTable
                            funcionarios={funcionarios}
                            agendamentoAction={abrirConfirmacao}
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
                    Confirma o Agendamento desse Aluno para este Funcionario?
                </Dialog>
            </div>
        </Card>




    )
}
export default CadastroAgendamento
import React, { useState } from "react";
import { useNavigate, useParams } from 'react-router-dom'

import Card from "../components/card";
import FormGroup from "../components/form-group";
import SelectMenu from '../components/selectMenu'
import AlunoService from '../service/alunoService'
import LocalStorageService from "../service/localstorageService";
import * as messages from '../components/toastr'
import { render } from "@testing-library/react";

import { CpfCnpjMask } from '@react-br-forms/cpf-cnpj-mask';
const CadastroAluno = ({ }) => {

    const navigate = useNavigate();
    const [nome, setNome] = React.useState('');
    const [email, setEmail] = React.useState('');
    const [cpf, setCPF] = React.useState('');

    const [genero, setGenero] = React.useState('');
    const [tipo, setTipo] = React.useState('');
    const [telefone, setTelefone] = React.useState('');
    const [status, setStatus] = React.useState('EFETIVADO');
    const service = new AlunoService();
    const alunoDados = {
        nome, email, cpf, telefone, genero, tipo,status
    };
    const [atualizando, setAtualizando] = React.useState(false);


    const params = useParams();
    if(params.id){
            service
                .obterPorId(params.id)
                .then(response => {
                    setNome(response.data.nome);
                    setEmail(response.data.email);
                    setCPF(response.data.cpf);
                    setGenero(response.data.genero);
                    setTipo(response.data.tipo);
                    setTelefone(response.data.telefone);
                    setStatus(response.data.status);
                    setAtualizando(true);

                })
                .catch(erros => {
                    messages.mensagemErro(erros.response.data);
                })
        }



    const submit = () => {
        try {
            service.validar(alunoDados)
        }
        catch (erro) {
            const mensagens = erro.mensagens;
            mensagens.forEach(msg => messages.mensagemErro(msg));
            return false;

        }
        service.salvar(alunoDados)
            .then(resposta => {
                navigate("/buscar-aluno");
                messages.mensagemSucesso('Aluno Cadastrado Com Sucesso.');
            })
            .catch(error => {
                messages.mensagemErro(error.response.data)
            });


    }
    const atualizar = () => {
        try {
            service.validar(alunoDados)
        }
        catch (erro) {
            const mensagens = erro.mensagens;
            mensagens.forEach(msg => messages.mensagemErro(msg));
            return false;

        }
        service.atualizar(alunoDados)
            .then(resposta => {
                navigate("/buscar-aluno");
                messages.mensagemSucesso('Aluno Atualizado Com Sucesso.');
            })
            .catch(error => {
                messages.mensagemErro(error.response.data)
            });


    }

    const handleChange = (event) => {
        const { name, value } = event.target;
        if (name == 'nome') { setNome(value); }
        if (name == 'email') { setEmail(value); }
        if (name == 'cpf') { setCPF(value); }
        if (name == 'genero') { setGenero(value); }
        if (name == 'tipo') { setTipo(value); }
        if (name == 'telefone') { setTelefone(value); }

    }


    const generos = service.obterTipoGenero();
    const tipos = service.obterTipoAluno();
    const statuss = service.obterTipoStatus();
    return (
        <Card title={atualizando ? 'Atualização de Aluno' : 'Cadastro de Aluno'}>
            <div className="row">
                <div className="col-md-12">
                    <FormGroup id="inputNome" label="Nome:*" >
                        <input id="inputNome" type="text"
                            className="form-control"
                            name="nome"
                            value={nome}
                            onChange={handleChange} />
                    </FormGroup>
                </div>
            </div>
            <div className="row">
                <div className="col-md-12">
                    <FormGroup id="inputEmail" label="Email: *" >
                        <input id="inputEMail" type="email"
                            className="form-control"
                            name="email"
                            value={email}
                            onChange={handleChange} />
                    </FormGroup>
                </div>
            </div>
            <div className="row">
                <div className="col-md-12">
                    <FormGroup id="inputCPF" label="CPF: *" >
                        <input id="inputCPF" type="text"
                            className="form-control"
                            name="cpf"
                            value={cpf}
                            onChange={handleChange}
                            placeholder="Digite o CPF"
                            {...CpfCnpjMask} />
                    </FormGroup>
                </div>
            </div>

            <div className="row">
                <div className="col-md-4">
                    <FormGroup id="inputTelefone" label="Telefone: *">
                        <input id="inputTelefone"
                            type="text"
                            name="telefone"
                            value={telefone}
                            onChange={handleChange}
                            className="form-control" />
                    </FormGroup>
                </div>
                <div className="col-md-4">
                    <FormGroup id="inputTipo" label="Genero: *">
                        <SelectMenu id="inputTipo"
                            lista={generos}
                            name="genero"
                            value={genero}
                            onChange={handleChange}
                            className="form-control" />
                    </FormGroup>
                </div>

                <div className="col-md-4">
                    <FormGroup id="inputTipo" label="Tipo: *">
                        <SelectMenu id="inputTipo"
                            lista={tipos}
                            name="tipo"
                            value={tipo}
                            onChange={handleChange}
                            className="form-control" />
                    </FormGroup>
                </div>

                <div className="col-md-4">
                    <FormGroup id="inputStatus" label="Status: ">
                        <input type="text"
                            className="form-control"
                            name="status"
                            value={status}
                            disabled />
                    </FormGroup>
                </div>


            </div>
            <div className="row">
                <div className="col-md-6" style={{ margin: '2%' }}>
                    {atualizando ?
                        (
                            <button onClick={atualizar}
                                className="btn btn-success"  style={{ marginRight: '2%' }}>
                                <i className="pi pi-refresh"></i> Atualizar
                            </button>
                        ) : (
                            <button onClick={submit}
                                className="btn btn-success"  style={{ marginRight: '2%' }}>
                                <i className="pi pi-save"></i> Salvar
                            </button>
                        )
                    }
                    <button onClick={e => navigate('/recepcao')}
                        className="btn btn-danger " style={{ marginLeft: '2%' }}>
                        <i className="pi pi-times"></i>Cancelar
                    </button>
                </div>
            </div>
        </Card>




    )
}
export default CadastroAluno
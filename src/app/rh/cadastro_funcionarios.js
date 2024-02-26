import React, { useState } from "react";
import { useNavigate, useParams } from 'react-router-dom'

import Card from "../components/card";
import FormGroup from "../components/form-group";
import SelectMenu from '../components/selectMenu'
import FuncionarioService from '../service/funcionarioService'
import LocalStorageService from "../service/localstorageService";
import * as messages from '../components/toastr'
import { render } from "@testing-library/react";

import { CpfCnpjMask } from '@react-br-forms/cpf-cnpj-mask';
const CadastroFuncionario = ({ }) => {

    const navigate = useNavigate();
    const [nome, setNome] = React.useState('');
    const [email, setEmail] = React.useState('');
    const [cpf, setCPF] = React.useState('');
    const [senha, setSenha] = React.useState('');
    const [genero, setGenero] = React.useState('');
    const [tipo, setTipo] = React.useState('');
    const [telefone, setTelefone] = React.useState('');
    const [status, setStatus] = React.useState('EFETIVADO');
    const service = new FuncionarioService();
    const funcionarioDados = {
        nome, email, cpf, telefone, genero, tipo, senha,status
    };
    const [atualizando, setAtualizando] = React.useState(false);

    const params = useParams();
    if (params.id) {
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
                setSenha(response.data.senha);
                setAtualizando(true);

            })
            .catch(erros => {
                messages.mensagemErro(erros.response.data);
            })
    }


    const submit = () => {
        try {
            service.validar(funcionarioDados)
        }
        catch (erro) {
            const mensagens = erro.mensagens;
            mensagens.forEach(msg => messages.mensagemErro(msg));
            return false;

        }
        service.salvar(funcionarioDados)
            .then(resposta => {
                navigate("/buscar-funcionario");
                messages.mensagemSucesso('Funcionario Cadastrado Com Sucesso.');
            })
            .catch(error => {
                messages.mensagemErro(error.response.data)
            });


    }
    const atualizar = () => {
        try {
            service.validar(funcionarioDados)
        }
        catch (erro) {
            const mensagens = erro.mensagens;
            mensagens.forEach(msg => messages.mensagemErro(msg));
            return false;

        }
        service.atualizar(funcionarioDados)
            .then(resposta => {
                navigate("/buscar-funcionario");
                messages.mensagemSucesso('funcionario Atualizado Com Sucesso.');
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
        if (name == 'telefone') { setTelefone(value) };
        if (name == 'senha') {
            setSenha(value)
        }

    }


    const generos = service.obterTipoGenero();
    const tipos = service.obterTipoFuncionario();
    // const statuss = service.obterTipoStatus();
    return (
        <Card title={atualizando ? 'Atualização de Funcionario' : 'Cadastro de Funcionario'}>
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

                    <FormGroup label="Senha: *" htmlFor="inputSenha">
                        <input type="password"
                            id="inputSenha"
                            className="form-control"
                            name="senha"
                            onChange={handleChange} />
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
                                className="btn btn-success" style={{ marginRight: '2%' }}>
                                <i className="pi pi-refresh"></i> Atualizar
                            </button>
                        ) : (
                            <button onClick={submit}
                                className="btn btn-success" style={{ marginRight: '2%' }}>
                                <i className="pi pi-save"></i> Salvar
                            </button>
                        )
                    }
                    <button onClick={e => navigate('/admin')}
                        className="btn btn-danger " style={{ marginLeft: '2%' }}>
                        <i className="pi pi-times"></i>Cancelar
                    </button>
                </div>
            </div>
        </Card>

    )
}
export default CadastroFuncionario
import ApiService from '../service/apiservice'

import ErroValidacao from '../exception/ErroValidacao'

class FuncionarioService extends ApiService{

constructor()
{
super("/api/funcionarios")
}

autenticar(credenciais){
    return this.post('/autenticar', credenciais)
}

obterTipoFuncionario()
    {
        return[

            {label: 'Selecione...', value: '' },
            {label: 'Nutricionista', value: 'NUTRICIONISTA' },
            {label: 'Avaliador', value: 'AVALIADOR' },
            {label: 'Recepção', value: 'RECEPCAO' },
            {label: 'Outros', value: 'OUTROS' }
        ]

    }
    obterTipoGenero()
    {
        return[
            {label: 'Selecione...', value: '' },
            {label: 'Feminino', value: 'FEMININO' },
            {label: 'Masculino', value: 'MASCULINO' },
            {label: 'Outros', value: 'OUTROS' }
        ]

    }
    

    salvar(funcionario){
        return this.post('',funcionario)
    }
    validar(funcionario){
        const erros = []

        if(!funcionario.nome)
        {  erros.push('O campo Nome é obrigatório.')}
        if(!funcionario.email){
            erros.push('O campo Email é obrigatório.')
        }else if( !funcionario.email){
            erros.push('Informe um Email válido.')
        }

        if(!funcionario.cpf){
            erros.push("O campo CPF é obrigatório.")
        }
       if(!funcionario.senha)
       {
        erros.push("O campo Senha é obrigatório.")
       }
        if(!funcionario.telefone){
            erros.push("O campo Telefone é obrigatório.")
        }
        if(!funcionario.tipo){
            erros.push("Informe o Tipo.")
        }
        if(!funcionario.genero){
            erros.push("Informe um Genero.")
        } 
        if(erros && erros.length > 0){
            throw new ErroValidacao(erros);
        }
    }


    buscarporCPF(cpf){
        return this.get(`/buscarporCPF`, { cpf });
    }

    obterPorId(id){
        return this.get(`/buscarporId?id=${id}`);
    }

    alterarStatus(cpf, status){
        return this.put(`/${cpf}/atualizar-status`, { status })
    }
    atualizar(funcionario)
    {
        return this.put(`/${funcionario.id}/atualizar-id`, funcionario);
    }
    deletar(cpf){
        return this.delete(`/${cpf}`)
    }
    consultar(funcionarioFiltro){
        let params = ``
        if(funcionarioFiltro.nome){
            params = `${params}&nome=${funcionarioFiltro.nome}`
        }

        // if(funcionarioFiltro.genero){
        //     params = `${params}&genero=${funcionarioFiltro.genero}`
        // }

        if(funcionarioFiltro.tipo){
            params = `${params}&tipo=${funcionarioFiltro.tipo}`
        }

        // if(funcionarioFiltro.status){
        //     params = `${params}&status=${funcionarioFiltro.status}`
        // }

    
        return this.get(`/buscarFuncionarios?${params}`);
    }











}
export default FuncionarioService
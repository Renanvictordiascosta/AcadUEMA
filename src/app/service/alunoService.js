import ApiService from '../service/apiservice'

import ErroValidacao from '../exception/ErroValidacao'

class AlunoService extends ApiService{

    constructor(){
        super("/api/alunos")
    }

    obterTipoAluno()
    {
        return[
            {label: 'Selecione...', value: '' },
            {label: 'Uema', value: 'UEMA' },
            {label: 'Comunidade', value: 'COMUNIDADE' }
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
    obterTipoStatus()
    {
            return[
                {label: 'Selecione...', value: '' },
                {label: 'EFETIVADO', value: 'EFETIVADO' },
                {label: 'CANCELADO', value: 'CANCELADO' }

            ]


    }
    

    salvar(aluno){
        return this.post('',aluno)
    }
    validar(aluno){
        const erros = []

        if(!aluno.nome)
        {  erros.push('O campo Nome é obrigatório.')}
        if(!aluno.email){
            erros.push('O campo Email é obrigatório.')
        }else if( !aluno.email ){
            erros.push('Informe um Email válido.')
        }

        if(!aluno.cpf){
            erros.push("O campo CPF é obrigatório.")
        }
       
        if(!aluno.telefone){
            erros.push("O campo Telefone é obrigatório.")
        }
        if(!aluno.tipo){
            erros.push("Informe o Tipo.")
        }
        if(!aluno.genero){
            erros.push("Informe um Genero.")
        } 
        if(erros && erros.length > 0){
            throw new ErroValidacao(erros);
        }
    }

    
    obterPorId(id){
        return this.get(`/buscarporId?id=${id}`);
    }

    buscarporCPF(cpf){
        return this.get(`/buscarporCPF`, { cpf });
    }

    alterarStatus(cpf, status){
        return this.put(`/${cpf}/atualizar-status`, { status })
    }
    atualizar(aluno)
    {
        return this.put(`/${aluno.id}/atualizar-id`, aluno);
    }
    deletar(cpf){
        return this.delete(`/${cpf}`)
    }
    consultar(alunoFiltro){
        let params = ``
        if(alunoFiltro.nome){
            params = `${params}&nome=${alunoFiltro.nome}`
        }

        if(alunoFiltro.genero){
            params = `${params}&genero=${alunoFiltro.genero}`
        }

        if(alunoFiltro.tipo){
            params = `${params}&tipo=${alunoFiltro.tipo}`
        }

        if(alunoFiltro.status_do_aluno){
            params = `${params}&status=${alunoFiltro.status_do_aluno}`
        }

    
        return this.get(`/buscarAlunos?${params}`);
    }
}
export default AlunoService
import ApiService from '../service/apiservice'

import ErroValidacao from '../exception/ErroValidacao'

class AgendamentoService extends ApiService{

    constructor(){
        super("/api/agendamentos")
    }

    obterTipoAgendamento()
    {
        return[
            {label: 'Selecione...', value: '' },
            {label: 'Pendente', value: 'PENDENTE' },
            {label: 'Cancelado', value: 'CANCELADO' },
            {label: 'Efetivado', value: 'EFETIVADO' }
        ]

    }

    obterListaMeses(){
        return  [
            { label: 'Selecione...', value: '' },
            { label: 'Janeiro', value: 1 },
            { label: 'Fevereiro', value: 2 },
            { label: 'Março', value: 3 },
            { label: 'Abril', value: 4 },
            { label: 'Maio', value: 5 },
            { label: 'Junho', value: 6 },
            { label: 'Julho', value: 7 },
            { label: 'Agosto', value: 8 },
            { label: 'Setembro', value: 9 },
            { label: 'Outubro', value: 10 },
            { label: 'Novembro', value: 11 },
            { label: 'Dezembro', value: 12 },
        ]
    }

    salvar(agendamento){
        return this.post('',agendamento)
    }
    validar(agendamento){
        const erros = []

        if(!agendamento.horario)
        {  erros.push('O campo horario é obrigatório.')}
        if(!agendamento.ano){
            erros.push('O campo ano é obrigatório.')
        }

        if(!agendamento.dia){
            erros.push("O campo dia é obrigatório.")
        }
       
        if(!agendamento.mes){
            erros.push("O campo mes é obrigatório.")
        }
        // if(!agendamento.tipo){
        //     erros.push("Informe o Tipo.")
        // }

        if(!agendamento.aluno){
            erros.push("Informe um Aluno.")
        } 
        if(!agendamento.funcionario){
            erros.push("Informe um funcionario.")
        } 
        if(erros && erros.length > 0){
            throw new ErroValidacao(erros);
        }
    }

    deletar(id){
        return this.delete(`/${id}`)
    }

    alterarStatus(id, status){
        return this.put(`/${id}/atualizar-status`, { status })
    }
    consultar(agendamentoFiltro){
        let params = ``
        if(agendamentoFiltro.horario){
            params = `${params}&horario=${agendamentoFiltro.horario}`
        }

        if(agendamentoFiltro.dia){
            params = `${params}&dia=${agendamentoFiltro.dia}`
        }

        if(agendamentoFiltro.mes){
            params = `${params}&mes=${agendamentoFiltro.mes}`
        }

        if(agendamentoFiltro.ano){
            params = `${params}&ano=${agendamentoFiltro.ano}`
        }

        if(agendamentoFiltro.aluno){
            params = `${params}&aluno=${agendamentoFiltro.aluno}`
        }

        if(agendamentoFiltro.funcionario){
            params = `${params}&funcionario=${agendamentoFiltro.funcionario}`
        }
        
    
        return this.get(`?${params}`);
    }
}

export default AgendamentoService
import ApiService from '../service/apiservice'

import ErroValidacao from '../exception/ErroValidacao'

class AdminService extends ApiService{

    constructor()
    {
    super("/api/admin")
    }
    
    autenticar(credenciais){
        return this.post('/autenticar', credenciais)
    }
    
}
export default AdminService
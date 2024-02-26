import React from "react";
import Rotas from './app/main/rotas'
import ProvedorAutenticacao from "./app/main/provetorAutenticacao";

import 'toastr/build/toastr.min'
import 'bootswatch/dist/flatly/bootstrap.css'
import 'toastr/build/toastr.css'
import 'primeicons/primeicons.css'
import 'primereact/resources/themes/saga-purple/theme.css'
import "./App.css";
import "./app/decoração/login.css"
class App extends React.Component {
  render(){
    return(
      <ProvedorAutenticacao>
        <div className="container">    
            <Rotas />
        </div>
      </ProvedorAutenticacao>
    )
  }
}

export default App;

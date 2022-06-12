import React, { useContext } from 'react';
import { useInput } from '../hooks/useInputs';
import LoginContext from '../contextApi/LoginContext';
import { useNavigate, Link } from 'react-router-dom';
import carLogo from '../static/resources/minicarro.png'
import "../static/styles/login.css"

function LoginComponent() {
  const { value: username, bind: bindUsername, reset: resetUsername } = useInput('');
  const { value: password, bind: bindPassword, reset: resetPassword } = useInput('');
  const { getAuthorizationToken, authorizationToken } = useContext(LoginContext);
  const navigate = useNavigate();

  async function submitForm(event) {
    await getAuthorizationToken(username, password);
    resetUsername();
    resetPassword();
  }

  return (
    <div id="loginPage">
      <h2 id="loginTitle">Parking Spot App</h2>
      <form id="loginContainer">
        <img src={ carLogo } alt="car-logo" id="carLogoImage"/>
        <label htmlFor="username">
          <span>username: </span>
          <input type="text" id="username" {...bindUsername} />
        </label>
        <label htmlFor="password">
          <span>password: </span>
          <input type="password" id="password" {...bindPassword} />
        </label>

        <button type="button" onClick={ submitForm } >Log-in</button>
        { authorizationToken && authorizationToken.status === 400 && <h4>{ authorizationToken.content.message }</h4> }
        { authorizationToken && authorizationToken.status === 200 && navigate("/home")}
      </form>

      <Link to="/signup">or SignUp...</Link>
      <a href="https://www.flaticon.com/br/icones-gratis/mini-carro" title="mini carro ícones">Mini carro ícones criados por Freepik - Flaticon</a>
    </div>
    
  )
}

export default LoginComponent;

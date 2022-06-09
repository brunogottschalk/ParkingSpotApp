import React, { useContext } from 'react';
import { useInput } from '../hooks/useInputs';
import LoginContext from '../contextApi/LoginContext';
import { useNavigate } from 'react-router-dom';

function LoginComponent() {
  const { value: username, bind: bindUsername, reset: resetUsername } = useInput('');
  const { value: password, bind: bindPassword, reset: resetPassword } = useInput('');
  const { getAuthorizationToken, authorizationToken } = useContext(LoginContext);
  const navigate = useNavigate();

  async function submitForm() {
    await getAuthorizationToken(username, password);
    resetUsername();
    resetPassword();
  }

  return (
    <div>
      <form>

        <label htmlFor="username">
          <span>username: </span>
          <input type="text" id="username" {...bindUsername} />
        </label>
        <label htmlFor="password">
          <span>password: </span>
          <input type="password" id="password" {...bindPassword} />
        </label>

        <button type="button" onClick={ submitForm } >Log-in</button>
        { authorizationToken && authorizationToken.status === 400 && <h4>{ authorizationToken.content.message }</h4>}
        { authorizationToken && authorizationToken.status === 200 && navigate("/home")}
      </form>
    </div>
  )
}

export default LoginComponent;

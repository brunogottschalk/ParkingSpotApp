import { useInput } from "../hooks/useInputs";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import carLogo from "../static/resources/minicarro.png";
import LoginContext from "../contextApi/LoginContext";

function SignUpPageComponent() {
  const { signUpRequest, signUpResponse } = useContext(LoginContext);
  const { value: username, bind: bindUsername, reset: resetUsername } = useInput('');
  const { value: password, bind: bindPassword, reset: resetPassword } = useInput('');
  const navigate = useNavigate();

  function submitForm() {
    signUpRequest(username, password);
    resetUsername();
    resetPassword();
  }

  useEffect(() => {
    if (signUpResponse && signUpResponse.status === 201) {
      setTimeout(() => {
        navigate("/login");
      }, 1500)
    }
  }, [signUpResponse, navigate])

  return (
    <div id="loginPage">
    <h2 id="loginTitle">SignUp</h2>
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
      { signUpResponse && <h4 className="errorMessage">{ signUpResponse.content.message }</h4> }
    </form>
    <Link to="/login">or back to login page...</Link>
    <a href="https://www.flaticon.com/br/icones-gratis/mini-carro" title="mini carro ícones">Mini carro ícones criados por Freepik - Flaticon</a>
  </div>
  )

}

export default SignUpPageComponent
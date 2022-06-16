import { createContext, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import parkingSpotApi from '../services/ParkingSpotApi';

const LoginContext = createContext("")

function LoginContextProvider({ children }) {
  const navigate = useNavigate();
  const location = useLocation();
  const [authorizationToken, setAuthorizationToken] = useState(undefined);
  const [signUpResponse, setSignUpResponse] = useState(undefined);

  async function getAuthorizationToken(username, password) {
    const token = await parkingSpotApi.loginRequest(username, password);
    setAuthorizationToken(token);
  }

  useEffect(() => {
    if (!authorizationToken && location.pathname !== "/login" && location.pathname !== '/signup') {
      navigate("/login");
    }
  });

  async function signUpRequest(username, password) {
    const result = await parkingSpotApi.signUpRequest(username, password);
    setSignUpResponse(result);
  }

  const contextValues = {
    authorizationToken,
    getAuthorizationToken,
    signUpRequest,
    signUpResponse,
  };

  return (
    <LoginContext.Provider value={ contextValues }>{ children }</LoginContext.Provider>
  )
}

export { LoginContextProvider };
export default LoginContext;

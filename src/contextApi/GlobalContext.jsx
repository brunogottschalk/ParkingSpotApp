import { LoginContextProvider } from "./LoginContext";
import { CoreContextProvider } from './CoreContext';

function GlobalContext({ children }) {
  return (
    <LoginContextProvider>
      <CoreContextProvider>
        { children }
      </CoreContextProvider>
    </LoginContextProvider>
  )
}

export default GlobalContext;

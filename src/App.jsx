import { Routes, Route  } from 'react-router-dom'
import GlobalContext from './contextApi/GlobalContext';

import LoginComponent from './components/LoginComponent';
import HomePageComponent from './components/HomePageComponent';

function App() {
  return (
      <GlobalContext>
        <Routes>
          <Route path='/login' element={<LoginComponent />}></Route>
          <Route path="/home" element={ <HomePageComponent />}></Route>
        </Routes>
      </GlobalContext>
  );
}

export default App;

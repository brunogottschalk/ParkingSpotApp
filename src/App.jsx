import { Routes, Route  } from 'react-router-dom'
import GlobalContext from './contextApi/GlobalContext';

import LoginComponent from './components/LoginComponent';
import HomePageComponent from './components/HomePageComponent';
import NewParkingComponent from './components/NewParkingComponent';

function App() {
  return (
      <GlobalContext>
        <Routes>
          <Route path='/login' element={<LoginComponent />}></Route>
          <Route path="/home" element={ <HomePageComponent />}></Route>
          <Route path="/parking/:id" element={ <NewParkingComponent/> }></Route>
        </Routes>
      </GlobalContext>
  );
}

export default App;

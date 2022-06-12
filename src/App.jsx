import { Routes, Route, Navigate, useLocation  } from 'react-router-dom'
import GlobalContext from './contextApi/GlobalContext';

import LoginComponent from './components/LoginComponent';
import HomePageComponent from './components/HomePageComponent';
import NewParkingComponent from './components/NewParkingComponent';
import HistoryComponent from './components/HistoryComponent';
import PaymentPageComponent from './components/PaymentPageComponent';

function App() {
  const location = useLocation();
  return (
      <GlobalContext>
        <Routes>
          <Route exact path='/login' element={<LoginComponent />}></Route>
          <Route exact path="/home" element={ <HomePageComponent />}></Route>
          <Route exact path="/parking/:id" element={ <NewParkingComponent/> }></Route>
          <Route exact path="/history" element={ <HistoryComponent /> } />
          <Route exact path="/payment" element={ <PaymentPageComponent /> } />
        </Routes>
        { location.pathname === "/" && <Navigate to="/login" />} 
      </GlobalContext>
  );
}

export default App;

import { Routes, Route } from 'react-router-dom'
import GlobalContext from './contextApi/GlobalContext';

import LoginComponent from './components/LoginComponent';
import HomePageComponent from './components/HomePageComponent';
import NewParkingComponent from './components/NewParkingComponent';
import HistoryComponent from './components/HistoryComponent';
import PaymentPageComponent from './components/PaymentPageComponent';
import SignUpPageComponent from './components/SignUpPageComponent';

function App() {
  return (
      <GlobalContext>
        <Routes>
          <Route exact path='/login' element={<LoginComponent />}></Route>
          <Route exact path="/home" element={ <HomePageComponent />}></Route>
          <Route exact path="/parking/:id" element={ <NewParkingComponent/> }></Route>
          <Route exact path="/history" element={ <HistoryComponent /> } />
          <Route exact path="/payment" element={ <PaymentPageComponent /> } />
          <Route exact path="/signup" element={ <SignUpPageComponent /> } />
        </Routes>
      </GlobalContext>
  );
}

export default App;

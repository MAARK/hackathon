import './App.css';
import TopNavigation from './components/TopNavigation';
import Sidebar from './components/Sidebar';
import Dashboard from './components/Dashboard';
import Search from './components/Search';
import MainContent from './components/MainContent';
import Users from './components/Users';
import Behaviors from './components/Behaviors';
import 'mapbox-gl/dist/mapbox-gl.css';

function App() {
  return (
    <div className="App">
      <TopNavigation />
      <div className='wrapper'>
        <Sidebar />
        <MainContent>
        </MainContent>
      </div>
    </div>
  );
}

export default App;

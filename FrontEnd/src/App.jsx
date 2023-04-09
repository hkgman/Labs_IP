
import { useRoutes, Outlet, BrowserRouter } from 'react-router-dom';
import Header from './components/common/Header';
import Catalogs from './components/catalogs/MainPage';
import CatalogGroups from './components/catalogs/Autorize';
import CatalogStudents from './components/catalogs/News';
import Post from './components/catalogs/Post';
import Reports from './components/catalogs/RezhimRaboty';
import ReportGroupStudents from './components/catalogs/Raspisanie';
import Footer from './components/common/Footer';

function Router(props) {
  return useRoutes(props.rootRoute);
}

export default function App() {
  const routes = [
    { index: true, element: <Catalogs /> },
    { path: 'Main_page', element: <Catalogs />, label: 'Главная' },
    { path: 'News', element: <CatalogStudents/>, label:'Новости' },
    { path: 'Autorize', element: <CatalogGroups />, label:'Авторизация'},
    { path: 'RezhimRaboty', element: <Reports />, label: 'Режим работы' },
    { path: 'Raspisanie', element: <ReportGroupStudents />,label:'Расписание' },
    { path: 'Post', element: <Post/>}
  ];
  const links = routes.filter(route => route.hasOwnProperty('label'));
  const rootRoute = [
    { path: '/', element: render(links), children: routes }
  ];

  function render(links) {
    return (
      <div className='body_app'>
        <Header links={links} />
        <div className="d-flex text-white bg-info bg-gradient fw-bold">
          <Outlet />
        </div>
        <Footer links={links}/>
      </div>
    );
  }

  return (
    <BrowserRouter>
      <Router rootRoute={ rootRoute } />
    </BrowserRouter>
  );
}
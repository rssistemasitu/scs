import Header from './presentation/components/Header'
import { ShowCase } from './presentation/components/ShowCase';
import MoviesCard from './presentation/components/MoviesCard';
import Footer from './presentation/components/Footer';
import { categories } from './presentation/api/Api'

function App() {

  return (
    <div className='w-full min-h-screen bg-black '>
      {/* Header */}
      <div className="w-full sticky z-20 top-0">
        <Header />
      </div>

      {/* Main Content */}
      <div className="max-w-7xl z-10 mx-auto">

        <div className="showcase">
          <ShowCase />
        </div>

        {categories.map((category, index) => (
            <MoviesCard key={index} title={category.title} path={category.path} />
        ))}       

      </div>
      <Footer />
    </div>
  )
}

export default App

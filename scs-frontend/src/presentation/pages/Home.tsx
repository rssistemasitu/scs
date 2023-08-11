import { ShowCase } from '../components/ShowCase'
import { categories } from '../api/Api'
import MoviesCard from '../components/MoviesCard'
import UseAuth from '../hooks/UseAuth';

export const Home = () => {
  return (
      //Main Content
      <div className="max-w-7xl z-10 mx-auto">

        <div className="showcase">
          <ShowCase />
        </div>

        {categories.map((category, index) => (
            <MoviesCard key={index} title={category.title} path={category.path}/>
        ))}       

      </div>
  )
}

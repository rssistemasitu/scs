import { EyeIcon, StarIcon } from '@heroicons/react/24/solid'
import { motion } from 'framer-motion';
import { useEffect, useRef, useState } from 'react';
import { Movie, getMovies } from '../api/Api';
import Spinner from './Spinner';
import UseAuth from '../hooks/UseAuth';


const MoviesCard = ({title, path}) => {
  const { user } = UseAuth();

  const imageHost = 'https://image.tmdb.org/t/p/original'

  const [movies, setMovies] = useState<Movie[]>([]);
  const [loading, setLoading] = useState(true);

  const carousel = useRef();
  const [width, setWidth] = useState(0);

  useEffect(() => {
    const fetchMovies = async () => {
      const movieList = await getMovies(path, user.user.token);
      if (movieList) {
        setMovies(movieList);
        setLoading(false);
        setTimeout(() => {
          setWidth(carousel.current?.scrollWidth - carousel.current?.offsetWidth);
        }, 1000)
      }
    };

    fetchMovies();
  }, []);


  return (
    <div>

      { loading ? (<div className='flex justify-center items-center '><Spinner /></div>) :
        (

          <div className="w-full relative md:m-2 m-1 mx-1 -mt-12">
            {/* Movies Poster */}
              <div>
                <h2 className="text-md flex gap-1 my-4 mx-4 items-center text-white font-bold">
                  <StarIcon className='text-yellow-500 w-4' /> {title}
                </h2>

                <div className="w-full flex items-center p-1 overflow-x:hidden">

                  <div className='w-full max-w-[1400px] mx-auto  flex items-center justify-center'>

                    <motion.div className='cursor-grab overflow-hidden' ref={carousel} whileTap={{ cursor: 'grabbing' }}>
                      <motion.div className='flex'
                        drag='x'
                        dragConstraints={{ right: 0, left: - (width - 10) }}
                        initial={{ x: 100 }}
                        animate={{ x: 0 }}
                        transition={{ duration: 0.6 }}>

                        {movies && movies.map((movie, i) => (

                          <motion.div key={i} className='min-h-[200px] min-w-[230px] p-[14px] transition-all .2s hover:scale-105 hover:brightness-75'>
                            <img src={`${imageHost}` + movie.poster_path} alt="Texto" className='w-full h-auto rounded-md w-[200px] h-[90%] pointer-events-none' />
                            <div className="flex justify-between p-3">
                              <div className="flex items-center">
                                <StarIcon className='w-4 text-yellow-500' />
                                <StarIcon className='w-4 text-yellow-500' />
                                <StarIcon className='w-4 text-yellow-500' />
                                <StarIcon className='w-4 text-yellow-500' />
                                <StarIcon className='w-4 text-yellow-500' />
                                <StarIcon className='w-4 text-yellow-500' />
                              </div>

                              <span className="text-gray-300 flex items-center">
                                {movie.vote_count}
                                <EyeIcon className='w-4 mx-2' />
                              </span>
                            </div>
                          </motion.div>

                        ))}

                      </motion.div>
                    </motion.div>

                  </div>

                </div>
              </div>
            
          </div>
        )}</div>
  )
}     
      

export default MoviesCard
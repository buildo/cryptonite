import container from 'container';
import Products from './Products';
import { Book, Column } from 'model';
import { TransitionFunction } from 'state';

type MapProps = {
  transition: TransitionFunction,
  getProducts: Array<Book>,
  sortBy: Column,
  ascending: boolean
};

export default container(Products)({
  queries: ['getProducts'],
  connect: ['sortBy', 'ascending'],
  mapProps: ({ transition, getProducts = [], sortBy, ascending } : MapProps) => ({
    books: getProducts,
    sortBy,
    sortDir: ascending ? 'asc' : 'desc',
    onSortChange: ({ sortBy, sortDir }) => {
      transition({
        sortBy, ascending: sortDir === 'asc'
      })
    }
  })
}) as any as React.ComponentType;

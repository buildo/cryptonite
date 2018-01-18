import container from 'container';
import Products from './Products';
import { Book } from 'model';
import { TransitionFunction } from 'state';

type MapProps = {
  transition: TransitionFunction,
  getProducts: Array<Book>
};

export default container(Products)({
  queries: ['getProducts'],
  mapProps: ({ getProducts = []} : MapProps) => ({
    books: getProducts
  })
}) as any as React.ComponentType;

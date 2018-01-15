import * as React from 'react';
import { Book, Product, Amount } from '../../metarpheus/model-ts';
import { Tablo, Column, Cell, Header } from 'Basic/Tablo';

type Props = {
  books: Array<Book>
};
export default class Products extends React.PureComponent<Props> {
  render() {
    const data = this.props.books;
    return (
      <Tablo
       data={data}
      >
        <Column name='exchange' width={200}>
          <Header>Exchange</Header>
          <Cell>{ (exchange: string) => exchange}</Cell>
        </Column>

        <Column name='product' width={200}>
          <Header>Products</Header>
          <Cell>{ (product: Product) => `${product.base}-${product.quote}` }</Cell>
        </Column>

        <Column name='bid' width={200}>
          <Header>Bid</Header>
          <Cell>{ (amount: Amount) => `${amount.value} ${amount.currency}`}</Cell>
        </Column>

        <Column name='ask' width={200}>
          <Header>Ask</Header>
          <Cell>{ (amount: Amount) => `${amount.value} ${amount.currency}`}</Cell>
        </Column>

        <Column name='value' width={200}>
          <Header>Value</Header>
          <Cell>{ (amount: Amount) => `${amount.value} ${amount.currency}`}</Cell>
        </Column>
      </Tablo>
    )
  }
}

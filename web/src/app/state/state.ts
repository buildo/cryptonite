import init from 'buildo-state/lib';
import * as t from 'tcomb';
import { Column } from 'model';

export interface AppState {
  view: string,
  sortBy?: Column,
  ascending?: boolean
};
export const AppState = t.interface<AppState>({
  view: t.String,
  sortBy: t.Any,
  ascending: t.maybe(t.Boolean)
}, { strict: true });

export const { run, connect, appState } = init<AppState>(AppState);
import { TransitionFunction } from 'buildo-state/lib/transition';
export type TransitionFunction = TransitionFunction<AppState>;

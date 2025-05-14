import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import CreditCardList from '../CreditCardList';
import axios from 'axios';

// Mock axios
jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;

// Create a new QueryClient for each test
const createTestQueryClient = () => new QueryClient({
  defaultOptions: {
    queries: {
      retry: false,
    },
  },
});

// Wrapper component to provide QueryClient
const wrapper = ({ children }: { children: React.ReactNode }) => {
  const testQueryClient = createTestQueryClient();
  return (
    <QueryClientProvider client={testQueryClient}>
      {children}
    </QueryClientProvider>
  );
};

const mockCards = [
  {
    id: 1,
    name: 'John Doe',
    cardNumber: '4111111111111111',
    limit: 1000,
    balance: 0,
  },
  {
    id: 2,
    name: 'Jane Smith',
    cardNumber: '5555555555554444',
    limit: 2000,
    balance: 500,
  },
];

describe('CreditCardList', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders loading state initially', () => {
    mockedAxios.get.mockImplementation(() => new Promise(() => {}));

    render(<CreditCardList />, { wrapper });

    expect(screen.getByText(/loading credit cards/i)).toBeInTheDocument();
  });

  it('renders credit cards correctly', async () => {
    mockedAxios.get.mockResolvedValueOnce({ data: mockCards });

    render(<CreditCardList />, { wrapper });

    // Wait for cards to load
    await waitFor(() => {
      expect(screen.getByText('John Doe')).toBeInTheDocument();
      expect(screen.getByText('Jane Smith')).toBeInTheDocument();
    });

    // Verify card details
    expect(screen.getByText('4111111111111111')).toBeInTheDocument();
    expect(screen.getByText('5555555555554444')).toBeInTheDocument();
    expect(screen.getByText(/£\s*0\.00/)).toBeInTheDocument();
    expect(screen.getByText('£500.00')).toBeInTheDocument();
    expect(screen.getByText('£1000.00')).toBeInTheDocument();
    expect(screen.getByText('£2000.00')).toBeInTheDocument();
  });

  it('renders empty state when no cards exist', async () => {
    mockedAxios.get.mockResolvedValueOnce({ data: [] });

    render(<CreditCardList />, { wrapper });

    await waitFor(() => {
      expect(screen.getByText(/no credit cards found/i)).toBeInTheDocument();
    });
  });

  it('handles API error correctly', async () => {
    mockedAxios.get.mockRejectedValueOnce(new Error('Failed to fetch'));

    render(<CreditCardList />, { wrapper });

    await waitFor(() => {
      expect(screen.getByText(/failed to load credit cards/i)).toBeInTheDocument();
    });
  });

  it('formats currency values correctly', async () => {
    const cardsWithDecimals = [
      {
        id: 1,
        name: 'John Doe',
        cardNumber: '4111111111111111',
        limit: 1000.50,
        balance: 123.45,
      },
    ];

    mockedAxios.get.mockResolvedValueOnce({ data: cardsWithDecimals });

    render(<CreditCardList />, { wrapper });

    await waitFor(() => {
      expect(screen.getByText('£123.45')).toBeInTheDocument();
      expect(screen.getByText('£1000.50')).toBeInTheDocument();
    });
  });

  it('renders table headers correctly', async () => {
    mockedAxios.get.mockResolvedValueOnce({ data: mockCards });

    render(<CreditCardList />, { wrapper });

     // Wait for cards to load
     await waitFor(() => {
      expect(screen.getByText('John Doe')).toBeInTheDocument();
      expect(screen.getByText('Jane Smith')).toBeInTheDocument();
    });

    expect(screen.getByText('Name')).toBeInTheDocument();
    expect(screen.getByText('Card Number')).toBeInTheDocument();
    expect(screen.getByText('Balance')).toBeInTheDocument();
    expect(screen.getByText('Limit')).toBeInTheDocument();
  });
}); 
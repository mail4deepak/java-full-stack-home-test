import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import CreditCardForm from '../CreditCardForm';
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

describe('CreditCardForm', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders form fields correctly', () => {
    render(<CreditCardForm />, { wrapper });

    expect(screen.getByLabelText(/name/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/card number/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/limit/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /add/i })).toBeInTheDocument();
  });

  it('handles API error correctly', async () => {
    const errorMessage = 'Failed to add credit card';//Invalid card number
    mockedAxios.post.mockRejectedValueOnce({
      response: {
        data: {
          errors: {
            cardNumber: errorMessage,
          },
        },
      },
    });

    render(<CreditCardForm />, { wrapper });

    // Fill in the form
    fireEvent.change(screen.getByLabelText(/name/i), {
      target: { value: 'John Doe' },
    });
    fireEvent.change(screen.getByLabelText(/card number/i), {
      target: { value: '1234' },
    });
    fireEvent.change(screen.getByLabelText(/limit/i), {
      target: { value: '1000' },
    });

    // Submit the form
    fireEvent.click(screen.getByRole('button', { name: /add/i }));

    // Verify error message
    await waitFor(() => {
      expect(screen.getByText(errorMessage)).toBeInTheDocument();
    });
  });

  it('validates required fields', async () => {
    render(<CreditCardForm />, { wrapper });

    // Try to submit empty form
    fireEvent.click(screen.getByRole('button', { name: /add/i }));

    // Verify required field messages
    expect(screen.getByLabelText(/name/i)).toBeInvalid();
    expect(screen.getByLabelText(/card number/i)).toBeInvalid();
    expect(screen.getByLabelText(/limit/i)).toBeInvalid();
  });

  // Test for form submission success
  // Test for disables submit button while submitting
}); 
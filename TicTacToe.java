import java.util.Scanner;

public class TicTacToe {
   /* Defineix el nom de les constants
      1. Jugador: utilitza creuat i res
      2. Contingut cel·les: utilitza creuat, res i que no te "espai"
    */
   public static final int CROSS   = 0;
   public static final int NOUGHT  = 1;
   public static final int NO_SEED = 2;

   // Taula de joc
   public static final int ROWS = 3, COLS = 3;  // numero de files i columnes
   public static int[][] board = new int[ROWS][COLS]; // buit, creuat, en blanc o res

   // Jugador actual
   public static int currentPlayer;  // creuat, en blanc

   // defineix el nom de les constants per respresentar les formes de les variables
   public static final int PLAYING    = 0;
   public static final int DRAW       = 1;
   public static final int CROSS_WON  = 2;
   public static final int NOUGHT_WON = 3;
   // Com esta el joc (posicions, etc)
   public static int currentState;

   public static Scanner in = new Scanner(System.in); 

   /** COm que es el main, aqui comença el programa **/
   public static void main(String[] args) {
      // Activa la taula, currentState i el currentPlayer, basicament iniciar el joc
      initGame();

      // Juga nomes una vegada
      do {
         // currentPlayer fa un moviment
         // Actualizem taula de joc[selectedRow][selectedCol] i el currentState
         stepGame();
         // Actualizem la pantalla
         paintBoard();

         // escriu qui ha guanyat, perdut o si ha sigut un empat
         if (currentState == CROSS_WON) {
            System.out.println("'X' ha guanyat!");
         } else if (currentState == NOUGHT_WON) {
            System.out.println("'O' ha guanyat!");
         } else if (currentState == DRAW) {
            System.out.println("És un empat!");
         }
         // Canviar currentPlayer
         currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
      } while (currentState == PLAYING); // si no es game over repetir fins que ho sigui
   }

   /** Iniciar la taula (board[][]), currentState i currentPlayer per començar un joc nou **/
   public static void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = NO_SEED;  // netejar les cel·les, posar-les sense res
         }
      }
      currentPlayer = CROSS;   // primer es juga amb un cross
      currentState  = PLAYING; // apunt per jugar
   }

   /** currentPlayer fa un moviment
       Actualitzar la taula (board[selectedRow][selectedCol]) i el currentState. */
   public static void stepGame() {
      boolean validInput = false;  
      do {
         if (currentPlayer == CROSS) {
            System.out.print("Jugador 'X', digues el moviement (fila[1-3] columna[1-3]): ");
         } else {
            System.out.print("Jugador 'O', digues el moviment (fila[1-3] columna[1-3]): ");
         }
         int row = in.nextInt() - 1;  // index array comença per 0 
         int col = in.nextInt() - 1;
         if (row >= 0 && row < ROWS && col >= 0 && col < COLS
                      && board[row][col] == NO_SEED) {
            // Actualiza la taula --> board[][] i torna state del nou joc despres del moviment
            currentState = stepGameUpdate(currentPlayer, row, col);
            validInput = true;  // input okay, exit loop
         } else {
            System.out.println("El moviment a (" + (row + 1) + "," + (col + 1) + ") no es correcte. Torna a provar");
         }
      } while (!validInput);  // repeteix el validInput si input no es valid i ho va repetin fins que el final el valid
   }

   public static int stepGameUpdate(int player, int selectedRow, int selectedCol) {
      // Actualitzar taula de joc
      board[selectedRow][selectedCol] = player;

      // fa i retorna el nou game state
      if (board[selectedRow][0] == player       // 3 en fila
                && board[selectedRow][1] == player
                && board[selectedRow][2] == player
             || board[0][selectedCol] == player // 3 en columna
                && board[1][selectedCol] == player
                && board[2][selectedCol] == player
             || selectedRow == selectedCol      // 3 en diagonal
                && board[0][0] == player
                && board[1][1] == player
                && board[2][2] == player
             || selectedRow + selectedCol == 2  // 3 tres en el contrari de diagonal
                && board[0][2] == player
                && board[1][1] == player
                && board[2][0] == player) {
         return (player == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else {
         // si es un empat, ningú guanya, aixi que surt el missatge de empat
         for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
               if (board[row][col] == NO_SEED) {
                  return PLAYING; // encara hi han cel·les buides
               }
            }
         }
         return DRAW; // no hi han cel·les buides aixi que es un empat
      }
   }

   /** Ensenyar la taula de joc **/
   public static void paintBoard() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            paintCell(board[row][col]); // ensenyar totes les cel·les
            if (col != COLS - 1) {
               System.out.print("|");   // ensenyar la barra (|) que separa les cel·les de la part dels costats
            }
         }
         System.out.println();
         if (row != ROWS - 1) {
            System.out.println("-----------"); // ensenyar les barres(guions ------) que separen les cel·les de la part de dalt i de baix
         }
      }
      System.out.println();
   }

   /** Posa les fixes en el lloc que li hem dit **/
   public static void paintCell(int content) {
      switch (content) {
         case CROSS:   System.out.print(" X "); break;
         case NOUGHT:  System.out.print(" O "); break;
         case NO_SEED: System.out.print("   "); break;
      }
   }
}
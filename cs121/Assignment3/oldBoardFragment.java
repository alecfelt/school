/**
 * A simple {@link Fragment} subclass.
 */
public class OldBoardFragment extends Fragment implements View.OnTouchListener, Observer {


    BoardFragment.OnMenuSelectedListener mCallback;
    float width;
    float height;
    Paint paint;
    Bitmap b;
    ImageView iv;
    Canvas canvas;
    Observable ob;
    String turn;

    public OldBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = new View(getActivity());
        v.setOnTouchListener(this);
        v.post(new Runnable() {
            @Override
            public void run() {
                width = v.getMeasuredWidth();
                height = v.getMeasuredHeight();
//                b = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
//                canvas = new Canvas(b);
//                paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//                paint.setColor(Color.BLACK);
//                onDraw(canvas);
            }
        });
        ob = Model.getInstance();
        ob.addObserver(this);
        return v;
    }

    @Override
    public void update(Observable ob, Object o) { b.eraseColor(Color.TRANSPARENT); onDraw(canvas); }

    public void onDraw(Canvas c) {
//        if(Objects.equals(canvas, c)) {
//            int strokeWidth = 10;
//            paint.setColor(Color.BLACK);
//            paint.setStrokeWidth(strokeWidth);
//            for (int i = 1; i < 7; i++) {
//                int x = i * ((int) width / 7);
//                c.drawLine(x, 0, x, height, paint);
//            }
//            for (int i = 1; i < 6; i++) {
//                int y = i * ((int) height / 6);
//                c.drawLine(0, y, width, y, paint);
//            }
//            String[][] grid = mCallback.getGrid();
//            for (int i = 0; i < 6; i++) {
//                int y = ((i + 1) * ((int) height / 6)) - ((int) height / 12);
//                for (int j = 0; j < 7; j++) {
//                    int x = ((j + 1) * ((int) width / 7)) - ((int) width / 14);
//                    int r = Math.min(((int) width / 14), ((int) height / 12)) - 10;
//                    if (Objects.equals(grid[i][j], "red")) {
//                        paint.setColor(Color.RED);
//                        c.drawCircle(x, y, r, paint);
//                    }
//                    if (Objects.equals(grid[i][j], "blue")) {
//                        paint.setColor(Color.BLUE);
//                        c.drawCircle(x, y, r, paint);
//                    }
//                }
//            }
//            iv.setImageBitmap(b);
//        } else {
//            turn = mCallback.turn();
//            if(Objects.equals(turn, "red")) {
//                tb.eraseColor(Color.RED);
//            } else {
//                tb.eraseColor(Color.BLUE);
//            }
//            c = new Canvas(tb);
//            t.setImageBitmap(tb);
//        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent m) {
        int action = m.getAction();
        if(action == m.ACTION_UP) {
            float actionX = m.getX();
            int col = (int) (actionX / (width / 7));
            mCallback.touchAction(col);
        }
        return true;
    }

    // Container Activity must implement this interface
    public interface OnMenuSelectedListener {
        public void touchAction(int x);
        public String[][] getGrid();
        public String turn();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (BoardFragment.OnMenuSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMenuSelectedListener");
        }
        turn = mCallback.turn();
    }

}


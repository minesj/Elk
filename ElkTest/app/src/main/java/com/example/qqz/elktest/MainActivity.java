package com.example.qqz.elktest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qqz.elktest.adapter.BackListAdapter;
import com.example.qqz.elktest.adapter.FrontListAdapter;
import com.example.qqz.elktest.view.Rotate3dAnimation;
import com.example.qqz.elktest.view.SelectPicPopupLeftWindow;
import com.example.qqz.elktest.view.SelectPicPopupRightWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int FRONT_LIST_EXPAND_ANIMATION_DURATION = 1000;
    private static final int FRONT_LIST_LENGTH = 9;

    SelectPicPopupLeftWindow menuLeftWindow;
    SelectPicPopupRightWindow menuRightWindow;

    private ImageButton settingButton;
    private ImageButton shareButton;
    private TextView leftSettingTextview;
    private TextView rightSettingTextview;
    private ListView frontListView;
    private ListView backLsitView;
    private FrontListAdapter frontListAdapter;
    private BackListAdapter backListAdapter;
    private List<String> frontListData = new ArrayList<>();
    private List<String> backListData = new ArrayList<>();
    private boolean isFrontListExpanded;
    private int expandPosition;
    private int translationUp;
    private int translationDown;
    private int translationbackDown;
    private long firstTime = 0;
    private String headLeftTetxtView;
    private String headRightTetxtView;
    private ImageView remindLeft, remindRight, remindCenter;
    private TextView remindLeftText, remindRightText, remindCenterText, finshRemind;
    private Button finsh, leftFinshButton, rightFinshButton, centerFinshButton;
    private int variable = 1;
    private int num;
    private int clickItem;
    private int startx;
    private int starty;
    private View maskView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        isFrontListExpanded = false;
        frontListView = findViewById(R.id.front_list);
        backLsitView = findViewById(R.id.back_list);

        leftSettingTextview = findViewById(R.id.left_change);
        rightSettingTextview = findViewById(R.id.right_change);

        settingButton = findViewById(R.id.setting);
        shareButton = findViewById(R.id.share);

        frontListAdapter = new FrontListAdapter(this, frontListData);
        backListAdapter = new BackListAdapter(this, backListData);
        fillFrontListView();
        fillBackListview();

        frontListView.setAdapter(frontListAdapter);
        backLsitView.setAdapter(backListAdapter);

        leftSettingTextview.setOnClickListener(this);
        rightSettingTextview.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);


        frontListView.setOnItemClickListener(new OnFrontListViewItemClickedListener());
        frontListView.setOnTouchListener(new OnFrontListViewTouchListener());
        maskView = findViewById(R.id.maskview);
        remindLeft = findViewById(R.id.guideLeft);
        remindRight = findViewById(R.id.guideright);
        remindCenter = findViewById(R.id.guidecenter);
        remindLeftText = findViewById(R.id.listheadtext);
        remindRightText = findViewById(R.id.text2);
        remindCenterText = findViewById(R.id.text3);
        finshRemind = findViewById(R.id.text4);
        finsh = findViewById(R.id.button111);
        leftFinshButton = findViewById(R.id.leftbutton);
        rightFinshButton = findViewById(R.id.rightbutton);
        centerFinshButton = findViewById(R.id.centerbutton);


    }

    private float getVersionCode(Context context) {
        float versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0)
                    .versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_change:
                menuLeftWindow = new SelectPicPopupLeftWindow(MainActivity.this, itemsOnClick);
                menuLeftWindow.showAtLocation(MainActivity.this.findViewById(R.id.left_change),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                backgroundAlpha(0.5f);
                menuLeftWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });
                break;
            case R.id.right_change:
                menuRightWindow = new SelectPicPopupRightWindow(MainActivity.this, itemsOnClick);
                menuRightWindow.showAtLocation(MainActivity.this.findViewById(R.id
                                .right_change),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                backgroundAlpha(0.5f);
                menuRightWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);
                    }
                });
                break;
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SettingInterfaceActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void exchangeLeft() {
        headLeftTetxtView = (String) leftSettingTextview.getText();
        headRightTetxtView = (String) rightSettingTextview.getText();
        leftSettingTextview.setText(headRightTetxtView);
        rightSettingTextview.setText(headLeftTetxtView);
        final float centerX = frontListView.getWidth() / 2.0f;
        final float centerY = frontListView.getHeight() / 2.0f;
        Rotate3dAnimation animator = new Rotate3dAnimation(0f, 90f, centerX, centerY, 600f, true);
        animator.setDuration(250);
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
                backLsitView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                backLsitView.setVisibility(View.GONE);
                for (int i = 0; i <= FRONT_LIST_LENGTH; i++) {
                    ViewGroup LeftView = (ViewGroup) frontListView.getChildAt(i);
                    ViewGroup RightView = (ViewGroup) frontListView.getChildAt(i);
                    new exchangeData(LeftView, RightView).exchangeAnimation();
                }

                Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(270f, 360f,
                        centerX, centerY, 600f, false);
                rotate3dAnimation.setDuration(250);
                rotate3dAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        backLsitView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        backLsitView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                frontListView.startAnimation(rotate3dAnimation);
            }
        });
        frontListView.startAnimation(animator);

    }

    private void exchangeRight() {
        headLeftTetxtView = (String) leftSettingTextview.getText();
        headRightTetxtView = (String) rightSettingTextview.getText();
        leftSettingTextview.setText(headRightTetxtView);
        rightSettingTextview.setText(headLeftTetxtView);
        final float centerX = frontListView.getWidth() / 2.0f;
        final float centerY = frontListView.getHeight() / 2.0f;
        Rotate3dAnimation animator = new Rotate3dAnimation(360f, 270f, centerX, centerY, 600f,
                true);
        animator.setDuration(250);
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
                backLsitView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                backLsitView.setVisibility(View.GONE);
                for (int i = 0; i <= FRONT_LIST_LENGTH; i++) {
                    ViewGroup LeftView = (ViewGroup) frontListView.getChildAt(i);
                    ViewGroup RightView = (ViewGroup) frontListView.getChildAt(i);
                    new exchangeData(LeftView, RightView).exchangeAnimation();
                }
                Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(90f, 0f,
                        centerX, centerY, 600f, false);
                rotate3dAnimation.setDuration(250);
                rotate3dAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        backLsitView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        backLsitView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                frontListView.startAnimation(rotate3dAnimation);
            }
        });
        frontListView.startAnimation(animator);
    }

    private class exchangeData {
        private ViewGroup LeftView;
        private ViewGroup RightView;

        public exchangeData(ViewGroup LeftView, ViewGroup RightView) {
            this.LeftView = LeftView;
            this.RightView = RightView;
        }

        public void exchangeAnimation() {
            final TextView frontLeftTextview = LeftView.findViewById(R.id.front_item_left_layout);
            final TextView frontRightTextview = RightView.findViewById(R.id
                    .front_item_right_layout);
            final String frontLeftData = (String) frontLeftTextview.getText();
            final String frontRightData = (String) frontRightTextview.getText();
            frontLeftTextview.setText(frontRightData);
            frontRightTextview.setText(frontLeftData);

        }
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.customize:
                    break;
                case R.id.leftexchange:
                    exchangeLeft();
                    menuLeftWindow.dismiss();
                    break;
                case R.id.rightexchange:
                    exchangeRight();
                    menuRightWindow.dismiss();
                    break;
                case R.id.changeleftmore:
                    int requestCode = 1;
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, CurrencyListLeftActivity.class);
                    intent.putExtra(CurrencyListLeftActivity.EXTRA_KEY_CURRENCY_SELECTED_CURRENCY,leftSettingTextview.getText());
                    ArrayList<String> currencies = new ArrayList<>();
                    currencies.add(leftSettingTextview.getText().toString());
                    currencies.add(rightSettingTextview.getText().toString());
                    intent.putStringArrayListExtra(
                            CurrencyListLeftActivity.EXTRA_KEY_SELECTED_CURRENCIES, currencies);
                    startActivityForResult(intent, requestCode);
                    break;
                case R.id.changerightmore:
                    int requestCode1 = 2;
                    Intent intent1 = new Intent();
                    intent1.setClass(MainActivity.this, CurrencyListRightActivity.class);
                    intent1.putExtra(CurrencyListLeftActivity.EXTRA_KEY_CURRENCY_SELECTED_CURRENCY,rightSettingTextview.getText());
                    ArrayList<String> currencies1 = new ArrayList<>();
                    currencies1.add(leftSettingTextview.getText().toString());
                    currencies1.add(rightSettingTextview.getText().toString());
                    intent1.putStringArrayListExtra(
                            CurrencyListLeftActivity.EXTRA_KEY_SELECTED_CURRENCIES, currencies1);
                    startActivityForResult(intent1, requestCode1);
                    break;
                case R.id.leftcancel:
                    menuLeftWindow.dismiss();
                    break;
                case R.id.rightcancel:
                    menuRightWindow.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            String leftHeadText = data.getStringExtra("LeftTextView");
            leftSettingTextview.setText(leftHeadText);
            leftSettingTextview.setGravity(Gravity.CENTER_VERTICAL);
            menuLeftWindow.dismiss();
        } else if (resultCode == 2) {
            String rightHeadText = data.getStringExtra("RightTextView");
            rightSettingTextview.setText(rightHeadText);
            rightSettingTextview.setGravity(Gravity.CENTER_VERTICAL);
            menuRightWindow.dismiss();
        }
    }

    private void fillFrontListView() {
        frontListData = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            frontListData.add(String.format(Locale.ENGLISH, "%d", i * 10));
        }
        frontListAdapter.setData(frontListData);
        frontListAdapter.notifyDataSetChanged();
    }

    private void fillBackListview() {
        backListData = new ArrayList<>(9);
        //backListData.clear();
        clickItem = Integer.parseInt((String) frontListAdapter.getItem(expandPosition));
        for (int i = 1; i <= 9; i++) {
            num = clickItem + variable;
            backListData.add(String.valueOf(num));
            clickItem = clickItem + variable;
            backListAdapter.setData(backListData);
            backListAdapter.notifyDataSetChanged();
        }
    }

    private class OnFrontListViewItemClickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, final View view, final int position, long
                id) {
            isFrontListExpanded = true;
            expandPosition = position;
            translationUp = -(position) * view.getHeight();
            translationDown = (frontListAdapter.getCount() - (position + 2)) * view.getHeight();
            translationbackDown = view.getHeight();
            backLsitView.setTranslationY(translationbackDown);
            for (int i = 0; i <= position; i++) {
                View v = frontListView.getChildAt(i);
                ObjectAnimator animator = ObjectAnimator
                        .ofFloat(v, "TranslationY", v.getTranslationY(), translationUp);
                animator.setInterpolator(new BounceInterpolator());
                animator.setDuration(FRONT_LIST_EXPAND_ANIMATION_DURATION);
                animator.start();

            }
            for (int i = position + 1; i < frontListAdapter.getCount(); i++) {
                View v = frontListView.getChildAt(i);
                ObjectAnimator animator = ObjectAnimator
                        .ofFloat(v, "TranslationY", v.getTranslationY(), translationDown);
                animator.setInterpolator(new BounceInterpolator());
                animator.setDuration(FRONT_LIST_EXPAND_ANIMATION_DURATION);
                animator.start();
            }
            fillBackListview();
        }
    }


    private class OnFrontListViewTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (!isFrontListExpanded) {
                return false;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                isFrontListExpanded = true;
                for (int i = 0; i <= expandPosition; i++) {
                    View v = frontListView.getChildAt(i);
                    ObjectAnimator animator = ObjectAnimator
                            .ofFloat(v, "TranslationY", v.getTranslationY(), 0);
                    animator.setInterpolator(new BounceInterpolator());
                    animator.setDuration(FRONT_LIST_EXPAND_ANIMATION_DURATION);
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isFrontListExpanded = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isFrontListExpanded = false;
                        }
                    });
                }
                for (int i = expandPosition + 1; i < frontListAdapter.getCount(); i++) {
                    View v = frontListView.getChildAt(i);
                    ObjectAnimator animator = ObjectAnimator
                            .ofFloat(v, "TranslationY", v.getTranslationY(), 0);
                    animator.setInterpolator(new BounceInterpolator());
                    animator.setDuration(FRONT_LIST_EXPAND_ANIMATION_DURATION);
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isFrontListExpanded = true;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isFrontListExpanded = false;
                        }
                    });
                }
                if (isFrontListExpanded = true) {
                    return true;
                }
                isFrontListExpanded = false;
            }
            return false;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
        }
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
            return true;
        } else {
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            startx = (int) ev.getRawX();
            starty = (int) ev.getRawY();

        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (isFrontListExpanded) {
                return true;
            } else {
                int movex = (int) ev.getRawX();
                int dx = movex - startx;
                List<String> dataChange = new ArrayList<>();

                boolean isLeft = false;
                for (int i = 0; i <= FRONT_LIST_LENGTH; i++) {
                    if (dx < dp2px(this, -30) || dx > dp2px(this, 30)) {
                        return true;
                    } else if (dx > dp2px(this, 20)) {

                        isLeft = true;
                        String leftData = (String) frontListAdapter.getItem(i);
                        int leftChangeData = (int) (Float.valueOf(leftData) * 10);
                        dataChange.add(String.valueOf(leftChangeData));
                        if (leftChangeData > 100000000) {
                            Toast.makeText(MainActivity.this, "已达到最大值，无法再右滑",
                                    Toast.LENGTH_SHORT).show();
                            Vibrate();
                            return true;
                        }
                    } else if (dx < dp2px(this, -20)) {
                        isLeft = false;
                        String leftData = (String) frontListAdapter.getItem(i);
                        int leftChangeData = (int) (Float.valueOf(leftData) / 10);
                        dataChange.add(String.valueOf(leftChangeData));
                        if (leftChangeData < 10) {
                            Toast.makeText(MainActivity.this, "已达到最小值，无法再左滑",
                                    Toast.LENGTH_SHORT).show();
                            Vibrate();
                            return true;
                        }
                    } else {
                        frontListAdapter.Animation();
                        return true;
                    }
                }
                frontListData.clear();
                frontListData.addAll(dataChange);
                frontListAdapter.setX(dx);
                frontListAdapter.setData(frontListData);
                if (isLeft) {
                    Vibrate();
                    variable *= 10;
                } else {
                    Vibrate();
                    variable /= 10;
                }
            }
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            int movex = (int) ev.getRawX();
            int dx = movex - startx;
            if (Math.abs(dx) != 0) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void Vibrate() {
        Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = f;
        getWindow().setAttributes(lp);
    }

    private void setGuideView() {
        remindRight.setVisibility(View.VISIBLE);
        remindRightText.setVisibility(View.VISIBLE);
        rightFinshButton.setVisibility(View.VISIBLE);

        leftFinshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remindLeft.setVisibility(View.GONE);
                remindLeftText.setVisibility(View.GONE);
                leftFinshButton.setVisibility(View.GONE);
                remindCenter.setVisibility(View.VISIBLE);
                remindCenterText.setVisibility(View.VISIBLE);
                centerFinshButton.setVisibility(View.VISIBLE);
            }
        });
        rightFinshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remindLeft.setVisibility(View.VISIBLE);
                remindLeftText.setVisibility(View.VISIBLE);
                leftFinshButton.setVisibility(View.VISIBLE);
                rightFinshButton.setVisibility(View.GONE);
                remindRight.setVisibility(View.GONE);
                remindRightText.setVisibility(View.GONE);
            }
        });
        centerFinshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerFinshButton.setVisibility(View.GONE);
                remindCenter.setVisibility(View.GONE);
                remindCenterText.setVisibility(View.GONE);
                finsh.setVisibility(View.VISIBLE);
                finshRemind.setVisibility(View.VISIBLE);
            }
        });
        finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finsh.setVisibility(View.GONE);
                finshRemind.setVisibility(View.GONE);
                maskView.getBackground().setAlpha(100);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        float nowVersionCode = getVersionCode(MainActivity.this);
        SharedPreferences sp = getSharedPreferences("welcomecode", MODE_PRIVATE);
        float spVresionCode = sp.getFloat("spVersionCode", 0);
        if (nowVersionCode > spVresionCode) {
            setGuideView();
            SharedPreferences.Editor edit = sp.edit();
            edit.putFloat("spVersionCode", nowVersionCode);
            edit.commit();
        } else {
            maskView.setVisibility(View.GONE);
        }
    }
}
























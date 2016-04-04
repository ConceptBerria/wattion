package com.conceptberria.wattion.viewprice;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by ConceptBerria on 29/05/2014.
 * Preferencia de hora que mantiene los minutos en 0
 */

public class PickerPreferences extends DialogPreference implements TimePicker.OnTimeChangedListener {

    private static final int DEFAULT_VALUE = 0;
    private int mValue = -1;

    private TimePicker timePicker;


    public PickerPreferences(Context context) {
        this(context, null);
    }

    public PickerPreferences(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(R.layout.number_picker_layout);

        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);

    }

    @Override
    protected void onSetInitialValue(boolean restore, Object defaultValue) {
        setValue(restore ? getPersistedInt(DEFAULT_VALUE) : (Integer) defaultValue);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, DEFAULT_VALUE);
    }


    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);


        TextView dialogMessageText = (TextView) view.findViewById(R.id.text_dialog_message);
        dialogMessageText.setText(getDialogMessage());

        timePicker = (TimePicker) view.findViewById(R.id.number_picker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(mValue);
        timePicker.setCurrentMinute(0);
        timePicker.setOnTimeChangedListener(this);

    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
        persistInt(value);
        notifyChanged();

    }

    @Override
    public CharSequence getTitle() {

        return super.getTitle() + (mValue == -1 ? "" : " las " + mValue + ":00");
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        // when the user selects "OK", persist the new value
        if (positiveResult) {
            int numberPickerValue = timePicker.getCurrentHour();
            if (callChangeListener(numberPickerValue)) {
                setValue(numberPickerValue);
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        // save the instance state so that it will survive screen orientation changes and other events that may temporarily destroy it
        final Parcelable superState = super.onSaveInstanceState();

        // set the state's value with the class member that holds current setting value
        final SavedState myState = new SavedState(superState);
        myState.value = getValue();

        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // check whether we saved the state in onSaveInstanceState()
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // didn't save the state, so call superclass
            super.onRestoreInstanceState(state);
            return;
        }

        // restore the state
        SavedState myState = (SavedState) state;
        setValue(myState.value);

        super.onRestoreInstanceState(myState.getSuperState());
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int i, int i2) {
        //donthg
    }

    private static class SavedState extends BaseSavedState {
        int value;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);

            value = source.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            dest.writeInt(value);
        }

        @SuppressWarnings("unused")
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}


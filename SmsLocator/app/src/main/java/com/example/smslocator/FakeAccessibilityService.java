package com.example.smslocator;


import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class FakeAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) { }

    @Override
    public void onInterrupt() {

    }
}
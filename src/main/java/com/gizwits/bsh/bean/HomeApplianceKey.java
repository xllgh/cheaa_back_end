package com.gizwits.bsh.bean;

/**
 * 博西接口使用的Key
 */
public class HomeApplianceKey {

    public static final String powerState = "BSH.Common.Setting.PowerState";
    public class PowerStateValue {
        public static final String off = "BSH.Common.EnumType.PowerState.Off";
        public static final String on = "BSH.Common.EnumType.PowerState.On";
        public static final String standby = "BSH.Common.EnumType.PowerState.Standby";
    }

    public static final String doorState = "BSH.Common.Status.DoorState";
    public class DoorStateValue {
        public static final String open = "BSH.Common.EnumType.DoorState.Open";
        public static final String closed = "BSH.Common.EnumType.DoorState.Closed";
        public static final String locked = "BSH.Common.EnumType.DoorState.Locked";
    }

    public static final String operationState = "BSH.Common.Status.OperationState";
    public class OperationStateValue {
        public static final String inactive = "BSH.Common.EnumType.OperationState.Inactive";
        public static final String ready = "BSH.Common.EnumType.OperationState.Ready";
        public static final String delayedStart = "BSH.Common.EnumType.OperationState.DelayedStart";
        public static final String run = "BSH.Common.EnumType.OperationState.Run";
        public static final String pause = "BSH.Common.EnumType.OperationState.Pause";
        public static final String actionRequired = "BSH.Common.EnumType.OperationState.ActionRequired";
        public static final String finished = "BSH.Common.EnumType.OperationState.Finished";
        public static final String error = "BSH.Common.EnumType.OperationState.Error";
        public static final String aborting = "BSH.Common.EnumType.OperationState.Aborting";
    }

    public static final String settingFreezerTemperature = "Refrigeration.FridgeFreezer.Setting.SetpointTemperatureFreezer";
    public static final String settingFridgeTemperature = "Refrigeration.FridgeFreezer.Setting.SetpointTemperatureRefrigerator";
    public static final String settingFreezerSuperMode = "Refrigeration.FridgeFreezer.Setting.SuperModeFreezer";
    public static final String settingFridgeSuperMode = "Refrigeration.FridgeFreezer.Setting.SuperModeRefrigerator";

    public static final String ovenProgramHotAir = "Cooking.Oven.Program.HeatingMode.HotAir";
    public static final String ovenProgramTopBottomHeating = "Cooking.Oven.Program.HeatingMode.TopBottomHeating";
    public static final String ovenProgramPizzaSetting = "Cooking.Oven.Program.HeatingMode.PizzaSetting";
    public static final String ovenProgramOptionTemperature = "Cooking.Oven.Option.SetpointTemperature";
    public static final String ovenProgramOptionDuration = "BSH.Common.Option.Duration";

    public static final String dishwasherProgramAuto2 = "Dishcare.Dishwasher.Program.Auto2";
    public static final String dishwasherProgramEco50 = "Dishcare.Dishwasher.Program.Eco50";
    public static final String dishwasherProgramQuick45 = "Dishcare.Dishwasher.Program.Quick45";
    public static final String dishwasherProgramOptionDelayedStart = "BSH.Common.Option.StartInRelative";

    public static final String coffeeMakerProgramEspresso = "ConsumerProducts.CoffeeMaker.Program.Beverage.Espresso";
    public static final String coffeeMakerProgramEspressoMacchiato = "ConsumerProducts.CoffeeMaker.Program.Beverage.EspressoMacchiato";
    public static final String coffeeMakerProgramCoffee = "ConsumerProducts.CoffeeMaker.Program.Beverage.Coffee";
    public static final String coffeeMakerProgramCappuccino = "ConsumerProducts.CoffeeMaker.Program.Beverage.Cappuccino";
    public static final String coffeeMakerProgramLatteMacchiato = "ConsumerProducts.CoffeeMaker.Program.Beverage.LatteMacchiato";
    public static final String coffeeMakerProgramCaffeLatte = "ConsumerProducts.CoffeeMaker.Program.Beverage.CaffeLatte";
    public static final String coffeeMakerProgramOptionQuantity = "ConsumerProducts.CoffeeMaker.Option.FillQuantity";

    public static final String coffeeMakerProgramOptionTemperature = "ConsumerProducts.CoffeeMaker.Option.CoffeeTemperature";
    public class CoffeeMakerProgramOptionTemperatureValue {
        public static final String normal = "ConsumerProducts.CoffeeMaker.EnumType.CoffeeTemperature.Normal";
        public static final String high = "ConsumerProducts.CoffeeMaker.EnumType.CoffeeTemperature.High";
        public static final String veryHigh = "ConsumerProducts.CoffeeMaker.EnumType.CoffeeTemperature.VeryHigh";
    }

    public static final String coffeeMakerProgramOptionBean = "ConsumerProducts.CoffeeMaker.Option.BeanAmount";
    public class CoffeeMakerProgramOptionBeanValue {
        public static final String veryMild = "ConsumerProducts.CoffeeMaker.EnumType.BeanAmount.VeryMild";
        public static final String mild = "ConsumerProducts.CoffeeMaker.EnumType.BeanAmount.Mild";
        public static final String normal = "ConsumerProducts.CoffeeMaker.EnumType.BeanAmount.Normal";
        public static final String strong = "ConsumerProducts.CoffeeMaker.EnumType.BeanAmount.Strong";
        public static final String veryStrong = "ConsumerProducts.CoffeeMaker.EnumType.BeanAmount.VeryStrong";
        public static final String doubleShot = "ConsumerProducts.CoffeeMaker.EnumType.BeanAmount.DoubleShot";
        public static final String doubleShotPlus = "ConsumerProducts.CoffeeMaker.EnumType.BeanAmount.DoubleShotPlus";
        public static final String doubleShotPlusPlus = "ConsumerProducts.CoffeeMaker.EnumType.BeanAmount.DoubleShotPlusPlus";
    }

}
